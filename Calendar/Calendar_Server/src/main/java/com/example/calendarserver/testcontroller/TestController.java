package com.example.calendarserver.testcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@GetMapping("/sec/get")
	public ResponseEntity<String> get() {
		//TODO calendar data get
		return new ResponseEntity<String>("endpoint get is working",HttpStatus.NOT_IMPLEMENTED);
	}
	@PostMapping("/sec/post")
	public ResponseEntity<String> post() {
		//TODO calendar data post
		return new ResponseEntity<String>("endpoint post is working",HttpStatus.NOT_IMPLEMENTED);
	}
}
