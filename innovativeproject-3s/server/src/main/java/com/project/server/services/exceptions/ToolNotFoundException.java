package com.project.server.services.exceptions;

import com.project.server.model.Tool;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ToolNotFoundException extends RuntimeException {


    public ToolNotFoundException(Tool tool) {
        super("Could not find user {" + tool.getId() + ", " + tool.getName() + ", " + tool.getRepo() + "}");
    }

    public ToolNotFoundException(Long id) {
        super("Could not find user {id: " + id + "}");
    }

    public ToolNotFoundException(String name) {
        super("Could not find tool {name: " + name + "}");
    }
}

@ControllerAdvice
class ToolNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ToolNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String ToolNotFoundHandler(ToolNotFoundException ex) {
        return ex.getMessage();
    }
}