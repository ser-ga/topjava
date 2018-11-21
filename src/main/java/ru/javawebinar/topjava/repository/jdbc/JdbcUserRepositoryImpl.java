package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final ResultSetExtractor<List<User>> USER_WITH_ROLES_RS_EXTRACTOR = new ResultSetExtractor<>() {
        @Override
        public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
            List<Integer> orderList = new ArrayList<>();
            Map<Integer, User> users = new HashMap<>();
            while (rs.next()) {
                int user_id = rs.getInt("id");
                if (users.containsKey(user_id)) {
                    List<Role> roles = new ArrayList<>(users.get(user_id).getRoles());
                    roles.add(Role.valueOf(rs.getString("role")));
                    users.get(user_id).setRoles(roles);
                } else {
                    users.put(user_id, new User(user_id, rs.getString("name"), rs.getString("email"),
                            rs.getString("password"), rs.getInt("calories_per_day"),
                            rs.getBoolean("enabled"), rs.getDate("registered"),
                            List.of(Role.valueOf(rs.getString("role")))));
                    orderList.add(user_id);
                }
            }
            return orderList.stream().map(users::get).collect(Collectors.toList());
        }
    };

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
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
        insertRoles(new ArrayList<>(user.getRoles()), user.getId());
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT u.*, r1.role FROM users u " +
                "LEFT JOIN user_roles r1 ON u.id = r1.user_id " +
                "WHERE u.id = ?", USER_WITH_ROLES_RS_EXTRACTOR, id);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT u.*, r1.role FROM users u " +
                "LEFT JOIN user_roles r1 ON u.id = r1.user_id " +
                "WHERE u.email = ?", USER_WITH_ROLES_RS_EXTRACTOR, email);
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
//        return jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        return jdbcTemplate.query("SELECT u.*, r1.role FROM users u " +
                "LEFT JOIN user_roles r1 ON u.id = r1.user_id " +
                "ORDER BY u.name, u.email", USER_WITH_ROLES_RS_EXTRACTOR);
    }

    private void insertRoles(final List<Role> roles, Integer user_id) {

        String sql = "INSERT INTO user_roles (user_id, role) VALUES (?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Role role = roles.get(i);
                ps.setInt(1, user_id);
                ps.setString(2, role.name());
            }

            @Override
            public int getBatchSize() {
                return roles.size();
            }
        });
    }
}
