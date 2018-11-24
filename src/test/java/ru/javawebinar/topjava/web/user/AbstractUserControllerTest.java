package ru.javawebinar.topjava.web.user;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.web.AbstractControllerTest;

public class AbstractUserControllerTest extends AbstractControllerTest {

    @Autowired
    protected UserService userService;

    @BeforeEach
    protected void setUp() {
        cacheManager.getCache("users").clear();
        super.setUp();
    }
}
