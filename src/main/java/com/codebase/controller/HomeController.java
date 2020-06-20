package com.codebase.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "")
public class HomeController {

	@GetMapping(value = "")
	public String home() {
		return ("<h1>Welcome</h1>");
	}

}
