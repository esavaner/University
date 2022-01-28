package com.example.calendarserver.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	private UserService service;

	@Autowired
	public UserController(UserService service) {
		this.service = service;
	}

	@PostMapping("/user/register")
	public ResponseEntity<Void> register(@RequestBody UserDto user) {
		service.register(user);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
}
