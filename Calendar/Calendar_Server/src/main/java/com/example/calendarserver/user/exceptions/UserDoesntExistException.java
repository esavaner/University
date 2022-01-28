package com.example.calendarserver.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.BAD_REQUEST, reason="User doesnt exist")
public class UserDoesntExistException extends RuntimeException {
	private static final long serialVersionUID = -4166047927618552004L;
}
