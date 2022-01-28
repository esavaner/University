package com.project.server.services.exceptions;

import com.project.server.model.Tool;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ToolAlreadyExistsException extends  RuntimeException{

    public ToolAlreadyExistsException(Tool tool) {
        super("Tool with this name already exists " + tool.getName());
    }
}

@ControllerAdvice
class ToolAlreadyExistsAdvice {

    @ResponseBody
    @ExceptionHandler(ToolAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String ToolAlreadyExistsHandler(ToolAlreadyExistsException ex) {
        return ex.getMessage();
    }
}
