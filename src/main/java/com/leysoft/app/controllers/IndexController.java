package com.leysoft.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	@GetMapping(value = "/")
	public String index() {
		return "index";
	}
	
	@GetMapping(value = "/login")
	public String login() {
		return "user/login";
	}
	
	@GetMapping(value = "/denied")
	public String denied() {
		return "denied";
	}
}