package com.events.services;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.events.models.Event;
import com.events.repositories.EventRepository;

@Service
public class EventService {
    
	private final EventRepository eventRepository;
	
	public EventService(EventRepository EventRepository) {
		this.eventRepository = EventRepository;
	}
	
	// Retrieve all Events
	public List<Event> getAllEvents() {
		return eventRepository.findAll();
	}
	
	// Get all in-state events
	public List<Event> getInStateEvents(String state) {
		return eventRepository.findByState(state);
	}
	
	// Get all out-of-state events
	public List<Event> getOutOfStateEvents(String state) {
		return eventRepository.findByStateNot(state);
	}
	
	// Create an Event
	public Event createEvent(Event myEvent) {
		return eventRepository.save(myEvent); // Create new record with this Event
	}
	
	// get an Event
	public Event getEventById(Long id) {
		// Find Event, if possible
		Optional<Event> optionalEvent = eventRepository.findById(id);
        if(optionalEvent.isPresent()) { // If not null
            return optionalEvent.get(); // Return found Event
        } else {
            return null;
        }
	}
	
	// Update an Event
	public Event updateEvent(Long id, String eventName, Date eventDate, String city, String state) {
		Event myEvent = getEventById(id); // Find Event
		if (myEvent != null) {
			myEvent.setEventName(eventName);
			myEvent.setEventDate(eventDate);
			myEvent.setCity(city);
			myEvent.setState(state);
			eventRepository.save(myEvent); // Save changes
			return myEvent;
		} else { // No Event found
			return null;
		}
	}
	
	public Event updateEvent(Event myEvent) {
		return eventRepository.save(myEvent);
	}
	
	// Delete a Event
	public void deleteEvent(Long id) {
		if (eventRepository.existsById(id)) { // If Event with id exists
			eventRepository.deleteById(id); // Delete it
		} // If not, do nothing
	}
    public HashMap<String, String> makeStateList() {
    	//Use LinkedHashMap instead of just HashMap to have the states sorted from A-Z
    	LinkedHashMap<String, String> stateList = new LinkedHashMap<String, String>();
    	String[] states = {"AL","AK","AZ","AR","CA","CO","CT","DE","FL","GA","HI","ID","IL","IN","IA",
                "KS","KY","LA","ME","MD","MA","MI","MN","MS","MO","MT","NE","NV","NH","NJ",
                "NM","NY","NC","ND","OH","OK","OR","PA","RI","SC","SD","TN","TX","UT","VA",
                "VA","WA","WV","WI","WY"};
	    for (String s: states) {
	    	stateList.put(s, s);
	    }	
	    return stateList;
    }
    
}