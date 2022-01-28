package com.example.calendarserver.eventdata;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CountdownRepository extends CrudRepository<Countdown, Long> {
	List<Countdown> findByUserID(long userID);
}
