package com.example.calendarserver.eventdata;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.calendarserver.user.User;
import com.example.calendarserver.user.UserService;
import com.example.calendarserver.user.exceptions.UserDoesntExistException;

@Service
public class EventDataService {
	private final EventRepository eventRepository;
	private final CountdownRepository countdownRepository;
	private final UserService userService;

	@Autowired
	public EventDataService(UserService userService, EventRepository eventRepository,
			CountdownRepository countdownRepository) {
		this.userService = userService;
		this.eventRepository = eventRepository;
		this.countdownRepository = countdownRepository;
	}

	public List<Event> getAllEventsByUser(String username) {
		Optional<User> optUser = userService.getByLogin(username);
		User user = optUser.orElseThrow(UserDoesntExistException::new);
		return this.eventRepository.findByUserID(user.getId());
	}

	public List<Countdown> getAllCountdownsByUser(String username) {
		Optional<User> optUser = userService.getByLogin(username);
		User user = optUser.orElseThrow(UserDoesntExistException::new);
		return this.countdownRepository.findByUserID(user.getId());
	}

	public void saveEvents(String username, List<Event> events) {
		Optional<User> optUser = userService.getByLogin(username);
		User user = optUser.orElseThrow(UserDoesntExistException::new);
		for (Event e : events)
			if (e.getUserID() != user.getId())
				throw new OwnerMismatchException();
		this.eventRepository.saveAll(events);
	}

	public void saveCountdowns(String username, List<Countdown> countdowns) {
		Optional<User> optUser = userService.getByLogin(username);
		User user = optUser.orElseThrow(UserDoesntExistException::new);
		for (Countdown c : countdowns)
			if (c.getUserID() != user.getId())
				throw new OwnerMismatchException();
		this.countdownRepository.saveAll(countdowns);
	}

}
