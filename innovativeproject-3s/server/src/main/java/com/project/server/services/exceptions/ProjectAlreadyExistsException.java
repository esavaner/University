package com.project.server.services.exceptions;

import com.project.server.model.Project;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ProjectAlreadyExistsException extends RuntimeException {

    public ProjectAlreadyExistsException(Project project) {
        super("Project with this name already exists " + project.getName());
    }

    public ProjectAlreadyExistsException(String name) {
        super("Project with this name already exists " + name);
    }
}

@ControllerAdvice
class ProjectAlreadyExistsAdvice {

    @ResponseBody
    @ExceptionHandler(ProjectAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String ProjectAlreadyExistsHandler(ProjectAlreadyExistsException ex) {
        return ex.getMessage();
    }
}