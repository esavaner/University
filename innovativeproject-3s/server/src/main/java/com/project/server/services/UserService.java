package com.project.server.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.project.server.model.Permission;
import com.project.server.model.Project;
import com.project.server.services.exceptions.UserAlreadyExistsException;
import com.project.server.services.exceptions.UserNotFoundException;
import com.project.server.model.User;
import com.project.server.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    @Autowired
    UserRepository repository;

    public List<User> getUsers(User user) {
        User match = repository.findByEmail(user.getEmail()).orElseThrow(() -> new UserNotFoundException(user.getEmail()));
        if (isAdmin(match)) {
            return (List<User>) repository.findAll();
        } else {
            ArrayList<User> result = new ArrayList<>();
            result.add(match);
            return result;
        }
    }

    public List<User> getUsers() {
        return (List<User>) repository.findAll();
    }

    public User add(User user) {
        if (repository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(user);
        } else {
            return repository.save(user);
        }
    }

    public User getUserById(long id) {
        Optional<User> optionalusr = repository.findById(id);
        return (User) optionalusr.orElseThrow(() -> new UserNotFoundException(id));
    }

    public User getUserByEmail(String email) {
        Optional<User> user = repository.findByEmail(email);
        return (User) user.orElseThrow(() -> new UserNotFoundException(email));
    }

    public void delete(long id) {
        repository.deleteById(id);
    }

    public void changePassword(User user) {
        if (repository.findByEmail(user.getEmail()).isPresent()) {
            repository.findByEmail(user.getEmail()).get().setPassword(user.getPassword());
        } else {
            throw new UserNotFoundException(user.getEmail());
        }
    }

    public List<Project> getProjects(String email) {
        User user = getUserByEmail(email);
        return (List<Project>) repository.findByEmail(email).get().getProjects();
    }

    public static boolean isAdmin(User user) {
        for (Permission p : user.getPermissions()) {
            if (p.getType().equals("ADMIN")) {
                return true;
            }
        }

        return false;
    }
}
