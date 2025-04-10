package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class İndexController {

	@GetMapping("/")
	public String home() {
		return "index";
	}
	
}
