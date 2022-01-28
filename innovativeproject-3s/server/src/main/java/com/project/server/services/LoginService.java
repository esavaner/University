package com.project.server.services;

import com.project.server.services.exceptions.UserNotFoundException;
import com.project.server.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginService {

    @Autowired
    UserService service;

    public User loginValidate(String email, String password) {
        User match = service.getUserByEmail(email);
        if (match.getPassword().equals(password)) {
            return match;
        } else {
            throw new UserNotFoundException(email);
        }
    }
}