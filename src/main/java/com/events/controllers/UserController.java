package com.events.controllers;

import java.security.Principal;
import java.util.HashMap;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
	
	@GetMapping("/login")
	public String renderLoginReg(@RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, @ModelAttribute("user") User user, Model model) {
		// Make list of options for states
		HashMap<String, String> stateList = eventService.makeStateList();
		model.addAttribute("stateList", stateList);
		
		if(error != null) {
            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
        }
        if(logout != null) {
            model.addAttribute("logoutMessage", "Logout Successful!");
        }
        
		return "login-reg.jsp";
	}
	
	@PostMapping("/register")
	public String processRegistration(@Valid @ModelAttribute("user") User user, BindingResult result, HttpSession session, Model model) {
		userValidator.validate(user, result); // Validate with custom validator
		userValidator.validateEmail(user, result, userService);
		if (result.hasErrors()) {
			System.out.println(result.getAllErrors());
			// Make list of options for states
			HashMap<String, String> stateList = eventService.makeStateList();
			model.addAttribute("stateList", stateList);
    		return "login-reg.jsp"; // Go back to registration page instead of redirect so that error messages will pop up
		}
		else {
//			userService.saveWithUserRole(user);
			userService.saveUserWithAdminRole(user);
//			session.setAttribute("userId", user.getId());
			
		}
		return "redirect:/login";
	}
	
	@RequestMapping("/admin")
    public String adminPage(Principal principal, Model model) {
        String username = principal.getName();
        model.addAttribute("currentUser", userService.findByUsername(username));
        return "adminPage.jsp";
    }
	
	@RequestMapping(value = {"/", "/home"})
    public String home(Principal principal, Model model) {
        // 1
        String username = principal.getName();
        model.addAttribute("currentUser", userService.findByUsername(username));
        return "homePage.jsp";
    }
}
