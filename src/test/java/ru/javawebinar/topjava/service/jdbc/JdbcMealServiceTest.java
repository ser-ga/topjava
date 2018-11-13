package ru.javawebinar.topjava.service.jdbc;

import org.junit.Assume;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcMealServiceTest extends AbstractMealServiceTest {

    @Override
    @Test
    public void testValidation() throws Exception {
        Assume.assumeTrue("ignore JdbcMealServiceTest.testValidation",false);
    }
}