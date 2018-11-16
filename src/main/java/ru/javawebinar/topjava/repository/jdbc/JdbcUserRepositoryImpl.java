package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final RowMapper<User> USER_WITH_ROLES_MAPPER = (rs, rowNum) -> {
        Collection<Role> roles = new ArrayList<>();
        if (rs.getString("role_user") != null) roles.add(Role.ROLE_USER);
        if (rs.getString("role_admin") != null) roles.add(Role.ROLE_ADMIN);
        return new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"),
                rs.getString("password"), rs.getInt("calories_per_day"), rs.getBoolean("enabled"), rs.getDate("registered"), roles);
    };

    private static final String USER_WITH_ROLES_QUERY = "SELECT u.*, r1.role AS role_user, r2.role AS role_admin FROM users u " +
            "LEFT JOIN user_roles r1 ON u.id = r1.user_id AND r1.role = 'ROLE_USER' " +
            "LEFT JOIN user_roles r2 ON u.id = r2.user_id AND r2.role = 'ROLE_ADMIN' ";

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    private final SimpleJdbcInsert insertRole;

    @Autowired
    public JdbcUserRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        this.insertRole = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("user_roles")
                .usingColumns("user_id", "role");
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update(
                "UPDATE users SET name=:name, email=:email, password=:password, " +
                        "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
            return null;
        } else {
            jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", user.getId());
        }
        for(Role role : user.getRoles()) {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("user_id", user.getId());
            params.put("role", role);
            insertRole.execute(params);
        }
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query(USER_WITH_ROLES_QUERY +
                "WHERE u.id = ?", USER_WITH_ROLES_MAPPER, id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query(USER_WITH_ROLES_QUERY +
                "WHERE u.email = ?", USER_WITH_ROLES_MAPPER, email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
//        return jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        return jdbcTemplate.query(USER_WITH_ROLES_QUERY +
                "ORDER BY u.name, u.email", USER_WITH_ROLES_MAPPER);
    }
}
