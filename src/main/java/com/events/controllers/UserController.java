package com.events.controllers;

import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.events.models.User;
import com.events.services.EventService;
import com.events.services.UserService;
import com.events.validators.UserValidator;

@Controller
public class UserController {
	private final UserService userService;
	private final EventService eventService;
	private final UserValidator userValidator;
	
	public UserController(UserService userService, UserValidator userValidator, EventService eventService) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.eventService = eventService;
    }
	
	@GetMapping("/")
	public String renderLoginReg(@ModelAttribute("user") User user, Model model) {
		// Make list of options for states
		HashMap<String, String> stateList = eventService.makeStateList();
		model.addAttribute("stateList", stateList);
		return "login-reg.jsp";
	}
	
	@PostMapping("/register")
	public String processRegistration(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session, Model model) {
		userValidator.validate(user, result); // Validate with custom validator
		userValidator.validateEmail(user, result, userService);
		if (result.hasErrors()) {
			// Make list of options for states
			HashMap<String, String> stateList = eventService.makeStateList();
			model.addAttribute("stateList", stateList);
    		return "login-reg.jsp"; // Go back to registration page instead of redirect so that error messages will pop up
		}
		else {
			userService.registerUser(user);
			session.setAttribute("userId", user.getId());
			
		}
		return "redirect:/events";
	}
	
	@PostMapping("/login")
	public String processLogin(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session, @ModelAttribute("user") User user) {
		// if the user is authenticated, save their user id in session
		if (userService.authenticateUser(email, password)) {
    		session.setAttribute("userId", 				userService.findByEmail(email).getId());
    			return "redirect:/events";
    	}
		else {
		// else, add error messages and return the login page
		model.addAttribute("error", "Email or password is incorrect");
		// Make list of options for states
		HashMap<String, String> stateList = eventService.makeStateList();
		model.addAttribute("stateList", stateList);
		return "login-reg.jsp";
		}
	}
	
}
