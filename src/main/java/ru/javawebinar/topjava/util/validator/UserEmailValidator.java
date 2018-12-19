package ru.javawebinar.topjava.util.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.web.SecurityUtil;

public class UserEmailValidator implements Validator {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserTo.class.equals(clazz) || User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AuthorizedUser authorizedUser = SecurityUtil.safeGet();
        boolean isAdmin = authorizedUser != null && authorizedUser.getAuthorities().contains(Role.ROLE_ADMIN);
        if (target instanceof UserTo) {
            UserTo user = (UserTo) target;
            User stored = userRepository.getByEmail(user.getEmail());
            if (stored != null) {
                if (authorizedUser == null || // регистрация
                        //добавление юзера админом
                        (user.isNew() && isAdmin) ||
                        //апдейт юзера админом
                        (!user.isNew() && isAdmin && !stored.getId().equals(user.getId())) ||
                        //профиль пользователя
                        (!isAdmin && !stored.getId().equals(authorizedUser.getId()))) {
                    errors.rejectValue("email", "validate.email", "User with this email already exists");
                }
            }
        }

        if (target instanceof User) {
            User user = (User) target;
            User stored = userRepository.getByEmail(user.getEmail());
            if (stored != null) {
                if (!isAdmin || stored.getId().equals(authorizedUser.getId()))
                    errors.rejectValue("email", "validate.email", "User with this email already exists");
            }
        }
    }
}
