package com.example.calendarserver.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Missing credentials")
public class MissingCredentialsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2301760419886285638L;

}
