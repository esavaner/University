package com.project.server.controllers;

import com.project.server.model.User;
import com.project.server.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class LoginController {
    @Autowired
    LoginService service;

    @RequestMapping("/validateLogin")
    public User validateUser(@RequestHeader("Email") String email, @RequestHeader(value="Password") String password) {
        System.out.println("validateLogin (" + email + ") launched");
        return service.loginValidate(email, password);
    }
}