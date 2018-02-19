package com.leysoft.app.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.leysoft.app.services.inter.SecurityService;

@Controller
public class AjaxController {
	
	@Autowired
	private SecurityService securityService;
	
	@ResponseBody
	@GetMapping(value = "/ajax/ejemplo")
	public Map<String, Object> ejemplo() {
		Map<String, Object> response = new HashMap<>();
		Random random = new Random();
		if(securityService.isValid("ROLE_ADMIN")) {
			response.put("labels", new String[] {"One", "Two", "Three", "Four"});
			response.put("data", new int[] {random.nextInt(100) + 1, random.nextInt(100) + 1, random.nextInt(100) + 1, random.nextInt(100) + 1});
		}
		return response;
	}
}
