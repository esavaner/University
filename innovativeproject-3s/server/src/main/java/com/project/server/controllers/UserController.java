package com.project.server.controllers;

import java.util.List;

import com.project.server.model.User;
import com.project.server.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController

public class UserController {

    @Autowired UserService service;

    @GetMapping("/userTest")
    public User getUserTest() {
        return service.getUserByEmail("admin@nokia.com");
    }

    @RequestMapping("/users")
    public List<User> getUsers(@RequestHeader("Email") String email) {
        return service.getUsers();
    }

    @PostMapping("/users/add")
    public User addUser(@RequestBody User toAdd) {
        return service.add(toAdd);
    }

    @PostMapping("/users/edit")
    public void changePassword(@RequestBody User user) {
        service.changePassword(user);
    }
}
