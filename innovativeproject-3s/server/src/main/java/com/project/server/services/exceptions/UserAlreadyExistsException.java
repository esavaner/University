package com.project.server.services.exceptions;

import com.project.server.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(User user) {
        super("User with this email adress already exists " + user.getEmail());
    }
}

@ControllerAdvice
class UserAlreadyExistsAdvice {

    @ResponseBody
    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String UserAlreadyExistsHandler(UserAlreadyExistsException ex) {
        return ex.getMessage();
    }
}