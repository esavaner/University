package com.project.server.services.exceptions;

import com.project.server.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(User user) {
        super("Could not find user {" + user.getId() + ", " + user.getEmail() + ", " + user.getPassword() + "}");
    }

    public UserNotFoundException(Long id) {
        super("Could not find user {id: " + id + "}");
    }

    public UserNotFoundException(String email) {
        super("Could not find user {email: " + email + "}");
    }
}

@ControllerAdvice
class UserNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userNotFoundHandler(UserNotFoundException ex) {
        return ex.getMessage();
    }
}