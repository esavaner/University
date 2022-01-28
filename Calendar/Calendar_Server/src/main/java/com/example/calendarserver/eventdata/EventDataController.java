package com.example.calendarserver.eventdata;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventDataController {
	private EventDataService service;

	@Autowired
	public EventDataController(EventDataService service) {
		this.service = service;
	}

	@GetMapping("/sec/event/all")
	public ResponseEntity<List<Event>> getAllEvents(Authentication authentication) {
		List<Event> list = service.getAllEventsByUser(authentication.getName());
		return new ResponseEntity<List<Event>>(list, HttpStatus.OK);
	}

	@GetMapping("/sec/countdown/all")
	public ResponseEntity<List<Countdown>> getAllCountdowns(Authentication authentication) {
		List<Countdown> list = service.getAllCountdownsByUser(authentication.getName());
		return new ResponseEntity<List<Countdown>>(list, HttpStatus.OK);
	}

	@PostMapping("/sec/event/save")
	public ResponseEntity<Void> saveEvents(Authentication authentication, @RequestBody List<Event> events) {
		this.service.saveEvents(authentication.getName(), events);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@PostMapping("/sec/countdown/save")
	public ResponseEntity<Void> saveCountdowns(Authentication authentication, @RequestBody List<Countdown> countdowns) {
		this.service.saveCountdowns(authentication.getName(), countdowns);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
