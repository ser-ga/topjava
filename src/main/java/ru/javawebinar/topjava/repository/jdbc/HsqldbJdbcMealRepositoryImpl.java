package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Repository
@Profile("hsqldb")
public class HsqldbJdbcMealRepositoryImpl extends JdbcMealRepositoryImpl {


    public HsqldbJdbcMealRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    Timestamp convertLocalDateTime(LocalDateTime dateTime) {
        return Timestamp.valueOf(dateTime);
    }

}
