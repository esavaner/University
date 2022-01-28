package com.example.calendarserver.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.BAD_REQUEST, reason="Password too short")
public class PasswordTooShortException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2127322728367319519L;

}
