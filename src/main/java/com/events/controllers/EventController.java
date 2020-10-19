package com.events.controllers;


import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.events.models.Event;
import com.events.models.Message;
import com.events.models.User;
import com.events.services.EventService;
import com.events.services.MessageService;
import com.events.services.UserService;

@Controller
public class EventController {
	@Autowired
	private EventService eventService;
	@Autowired
	private UserService userService;
	@Autowired
	private MessageService messageService;
	
	@GetMapping("/events") //this is also the GET route for new event
	public String renderEvents (Model model, HttpSession session, @ModelAttribute("event") Event event) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) { //if user isn't logged in
			return "redirect:/"; // redirect to log in/reg page
		}
		else {
			User thisUser = userService.findUserById(userId);
			model.addAttribute("user", thisUser);
			model.addAttribute("events", eventService.getAllEvents());
			// Find in state events and add them to model
			model.addAttribute("inStateEvents", eventService.getInStateEvents(thisUser.getState()));
			
			// Find out of state events and add them to model
			model.addAttribute("outOfStateEvents", eventService.getOutOfStateEvents(thisUser.getState()));
			
			// Make list of options for states
			HashMap<String, String> stateList = eventService.makeStateList();
			model.addAttribute("stateList", stateList);
			return "eventsWithNew.jsp";
			
		}
	}
	
	@PostMapping("/new-event")
	public String createNewEvent(@Valid @ModelAttribute("event") Event event, BindingResult result, HttpSession session, Model model) {
		Date curDate = new Date();
		if (result.hasErrors()) {
			if (event.getEventDate() != null) {
				if (!event.getEventDate().after(curDate)) {
					model.addAttribute("error", "Event date must be after today.");
				}
			}
			// Make list of options for states
			HashMap<String, String> stateList = eventService.makeStateList();
			model.addAttribute("stateList", stateList);
			return "eventsWithNew.jsp";
		}
		else {
			//Get user id
			Long userId = (Long) session.getAttribute("userId");
			//Set current user to be event host
			event.setEventHost(userService.findUserById(userId));
			//Create event
			eventService.createEvent(event);
			return "redirect:/events";
		}
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.setAttribute("userId", null);
		return "redirect:/";
	}
	
	@DeleteMapping("/events/{eventIdStr}/delete")
	public String deleteEvent(@PathVariable("eventIdStr") String eventIdStr, @ModelAttribute("event") Event event, HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) { //if user isn't logged in
			return "redirect:/"; // redirect to log in/reg page
		}
		Long eventId = Long.parseLong(eventIdStr);
		eventService.deleteEvent(eventId);
		return "redirect:/events";
	}
	
	//Get route for update
	@GetMapping("/events/{eventIdStr}/edit")
	public String renderEditEvent(@PathVariable("eventIdStr") String eventIdStr, @ModelAttribute("event") Event event, HttpSession session, Model model) {
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) { //if user isn't logged in
			return "redirect:/"; // redirect to log in/reg page
		}
		
		Long eventId = Long.parseLong(eventIdStr);
		Event thisEvent = eventService.getEventById(eventId);
		if (thisEvent == null) { // If no event found
			return "redirect:/events"; // redirect to events page
		}
		
		if (thisEvent.getEventHost().getId() - userId != 0) {//If the logged in user is not the host of this event
			return "redirect:/events"; // redirect to events page
		}
		
		// Make list of options for states
		HashMap<String, String> stateList = eventService.makeStateList();
		model.addAttribute("stateList", stateList);
		
		model.addAttribute("event", eventService.getEventById(eventId));
		return "editEvent.jsp";
	}
	
	//Post route for update
	@PutMapping("/events/{eventIdStr}/edit")
	public String updateEvent(@PathVariable("eventIdStr") String eventIdStr, @Valid @ModelAttribute("event") Event event, BindingResult result, HttpSession session) {
		Long userId = (Long) session.getAttribute("userId");
		Long eventId = Long.parseLong(eventIdStr);
		Event thisEvent = eventService.getEventById(eventId);
		if (result.hasErrors()) {
            return "editEvent.jsp";
        } else {
        // Make sure other field NOT set in edit form are here as well (especially ID). Without this step, a new event will be created instead of having existing event updated.
        event.setId(Long.parseLong(eventIdStr));
        event.setEventHost(userService.findUserById(userId));
        event.setMessages(thisEvent.getMessages());
        event.setUsers(thisEvent.getUsers());
        
		eventService.updateEvent(event);
		return "redirect:/events";
        }
	}
	
	
	@GetMapping("/events/{eventIdStr}/join")
	public String addEventAttendee(@PathVariable("eventIdStr") String eventIdStr, @ModelAttribute("event") Event event, HttpSession session) {
		return toggleJoin(eventIdStr, event, session, "join");
	}
	
	@GetMapping("/events/{eventIdStr}/cancel")
	public String removeEventAttendee(@PathVariable("eventIdStr") String eventIdStr, @ModelAttribute("event") Event event, HttpSession session) {
		return toggleJoin(eventIdStr, event, session, "cancel");
	}
	
	
	private String toggleJoin(String eventIdStr, Event event, HttpSession session, String actionPath) {
		Long eventId = Long.parseLong(eventIdStr);
		Event thisEvent = eventService.getEventById(eventId);
		if (thisEvent == null) { // If no event found
			return "redirect:/events"; // redirect to events page
		}
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) { //if user isn't logged in
			return "redirect:/"; // redirect to log in/reg page
		}
		if (thisEvent.getEventHost().getId() == userId) {//If the logged in user is the host of this event
			return "redirect:/events"; // redirect to events page
		}
		User thisUser = userService.findUserById(userId);
		if (!thisEvent.getUsers().contains(thisUser) && actionPath.equals("join")) {//if user not already already in the event attendee list and the url is join
			thisEvent.getUsers().add(thisUser);
		}
		if (thisEvent.getUsers().contains(thisUser) && actionPath.equals("cancel")) {//if user is already already in the event attendee list and the url is cancel
			thisEvent.getUsers().remove(thisUser);
		}
			eventService.updateEvent(thisEvent);
			return "redirect:/events";
	}
	
	//Get route for new message. Get route for event details.
	@GetMapping("events/{eventIdStr}")
	public String renderOneEventPage(@ModelAttribute("message") Message message, @PathVariable("eventIdStr") String eventIdStr, @ModelAttribute("event") Event event, HttpSession session, Model model) {
		Long eventId = Long.parseLong(eventIdStr);
		Event thisEvent = eventService.getEventById(eventId);
		if (thisEvent == null) { // If no event found
			return "redirect:/events"; // redirect to events page
		}
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) { //if user isn't logged in
			return "redirect:/"; // redirect to log in/reg page
		}
		model.addAttribute("event", thisEvent);
		
		return "oneEventWithMsg.jsp";
	}
	
	//Post route for new message. 
	@PostMapping("events/{eventIdStr}")
	public String createMsg(@PathVariable("eventIdStr") String eventIdStr, HttpSession session, @Valid @ModelAttribute("message") Message message, BindingResult result, Model model) { //We start with a message in modelAttribute that has messageText. We need to link this message to the event and the user(poster), and save it in the database.
		Long userId = (Long) session.getAttribute("userId");
		if (userId == null) { //if user isn't logged in
			return "redirect:/"; // redirect to log in/reg page
		}
		
		//Get event
    	Long eventId = Long.parseLong(eventIdStr);
		Event thisEvent = eventService.getEventById(eventId);
		if (result.hasErrors()) {
			model.addAttribute("event", thisEvent);
            return "oneEventWithMsg.jsp";
        } else {
        	//Get user
        	User thisUser = userService.findUserById(userId);
        	// Link user to this message
        	message.setUser(thisUser);
        	
    		// Link event to this message
    		message.setEvent(thisEvent);
    		
    		// Create and save this message
    		messageService.createMessage(message); 
        }
		
		return "redirect:/events/" + eventIdStr;
	}
}
