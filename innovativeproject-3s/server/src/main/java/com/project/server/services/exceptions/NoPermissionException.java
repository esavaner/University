package com.project.server.services.exceptions;

import com.project.server.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class NoPermissionException extends RuntimeException {

    public NoPermissionException(User user) {
        super("No permission to this component for " + user.getEmail());
    }
}

@ControllerAdvice
class NoPermissionAdvice {

    @ResponseBody
    @ExceptionHandler(NoPermissionException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    String NoPermissionHandler(NoPermissionException ex) {
        return ex.getMessage();
    }
}