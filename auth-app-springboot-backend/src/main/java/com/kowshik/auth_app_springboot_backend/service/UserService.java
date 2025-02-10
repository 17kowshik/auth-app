package com.kowshik.auth_app_springboot_backend.service;

import com.kowshik.auth_app_springboot_backend.model.User;
import com.kowshik.auth_app_springboot_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;

    public String registerUser(String userName, String password) {
        if (getUserByUserName(userName) != null) {
            return "USER_ALREADY_EXISTS";
        }

        boolean validPassword = validatePasswordStrength(password);
        if (!validPassword) {
            return "PASSWORD_NOT_STRONG_ENOUGH";
        }

        User newUser = new User(userName, passwordService.hashPassword(password));
        userRepository.save(newUser);
        return "USER_REGISTERED_SUCCESSFULLY";
    }

    private boolean validatePasswordStrength(String password) {
        if (password.length() < 8) {
            return false;
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            }
            if (Character.isLowerCase(c)) {
                hasLower = true;
            }
            if (!Character.isLetterOrDigit(c)) {
                hasSpecial = true;
            }
        }

        if (hasUpper && hasLower && hasSpecial) {
            return true;
        } else {
            return false;
        }
    }

    public String login(String userName, String password) {
        User user = getUserByUserName(userName);
        if (user == null) {
            return "USER_NOT_FOUND";
        }
        boolean isPasswordValid = passwordService.validate(password, user.getPassword());
        if (isPasswordValid) {
            return "SUCCESS";
        } else {
            return "INVALID_CREDENTIALS";
        }
    }

    public User getUserByUserName(String userName) {
        return userRepository.findById(userName).orElse(null);
    }
}