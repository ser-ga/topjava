package ru.javawebinar.topjava.util.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.web.SecurityUtil;

public class UserEmailValidator implements Validator {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserTo.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UserTo user = (UserTo) target;
        User stored = userRepository.getByEmail(user.getEmail());
        AuthorizedUser authorizedUser = SecurityUtil.safeGet();
        if (stored != null) {
            if (authorizedUser == null || !stored.getId().equals(authorizedUser.getId())) {
                errors.rejectValue("email", "validate.email", "User with this email already exists");
            }
        }
    }
}
