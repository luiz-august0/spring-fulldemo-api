package com.springfulldemo.api.validators;

import com.springfulldemo.api.infrastructure.exceptions.ApplicationGenericsException;
import com.springfulldemo.api.infrastructure.exceptions.enums.EnumUnauthorizedException;
import com.springfulldemo.api.model.entities.User;
import com.springfulldemo.api.repository.UserRepository;
import com.springfulldemo.api.utils.Utils;
import com.springfulldemo.api.validators.classes.RequiredField;

import java.util.ArrayList;
import java.util.List;

public class UserValidator extends AbstractValidator {
    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;

        try {
            List<RequiredField> requiredFields = new ArrayList<>();
            requiredFields.add(new RequiredField(User.class.getDeclaredField("login"), "login"));
            requiredFields.add(new RequiredField(User.class.getDeclaredField("password"), "senha"));
            requiredFields.add(new RequiredField(User.class.getDeclaredField("role"), "nivel de acesso"));

            super.addListOfRequiredFields(requiredFields);
        } catch (Exception e) {
            throw new ApplicationGenericsException(e.getMessage());
        }
    }

    @Override
    public void validate(Object object) {
        User user = (User) object;

        super.validate(object);

        if (userRepository.existsByLoginAndIdIsNot(user.getLogin(), Utils.nvl(user.getId(), 0)))
            throw new ApplicationGenericsException(EnumUnauthorizedException.USER_ALREADY_REGISTERED);
    }
}