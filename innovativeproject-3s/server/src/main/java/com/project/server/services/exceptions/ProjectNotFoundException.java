package com.project.server.services.exceptions;

import com.project.server.model.Project;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ProjectNotFoundException extends RuntimeException {


    public ProjectNotFoundException(Project project) {
        super("Could not find Project {" + project.getId() + ", " + project.getName() + "}");
    }

    public ProjectNotFoundException(Long id) {
        super("Could not find Project {id: " + id + "}");
    }

    public ProjectNotFoundException(String name) {
        super("Could not find project {name: " + name + "}");
    }
}

@ControllerAdvice
class ProjectNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ProjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userNotFoundHandler(UserNotFoundException ex) {
        return ex.getMessage();
    }
}