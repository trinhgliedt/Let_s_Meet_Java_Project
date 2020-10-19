package com.events.repositories;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.events.models.Event;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
	// List all events
	List<Event> findAll();
	
	// Find all events in a state
	List<Event> findByState(String state);
	
	// Find all events not in a state
	List<Event> findByStateNot(String state);
}