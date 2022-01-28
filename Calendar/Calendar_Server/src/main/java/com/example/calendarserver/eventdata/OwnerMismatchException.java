package com.example.calendarserver.eventdata;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Authenticated user differs from data owner")
public class OwnerMismatchException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2198171080028932895L;

}
