package com.example.demo.controller;

import org.hibernate.usertype.UserVersionType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.Users;
import com.example.demo.services.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsersController {

	@Autowired
	UserService usrev;

	@GetMapping("/map-register")
	public String registerMapping() {

		return "register";
	}

	@PostMapping("/register")
	public String addUser(@ModelAttribute Users user) {
		boolean userstatus = usrev.emailExists(user.getEmail());
		if (userstatus == false) {
			usrev.addUser(user);
			return "registersuccess";
		} else {
			return "registerfail";
		}

	}

	@PostMapping("/login")
	public String validateUser(@RequestParam String email, @RequestParam String password,HttpSession session) {

		//invoking validateUser() in Service
		if (usrev.validateUser(email, password) == true) {

			session.setAttribute("email",email);
			
			if (usrev.getRole(email).equals("admin")) {
				return "adminhome";
			} else {
				return "customerhome";
			}

		} else {
			return "loginfail";
		}
	}
	
	@GetMapping("/exploreSongs")
	public String exploreSongs(HttpSession session) {
		
		String email= (String) session.getAttribute("email");
		Users user = usrev.getUser(email);
		Boolean userStatus=user.isPremium();
		if(userStatus == true) {
			return "displaysongs";
		}
		else {
			return "payment";
		}
	}
}
