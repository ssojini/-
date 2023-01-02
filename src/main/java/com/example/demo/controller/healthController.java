package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ch.qos.logback.core.model.Model;

@Controller
@RequestMapping("/health")
public class healthController {
	@GetMapping("/freeboard")
	public String main(Model m) {
		return "html/freeboard";
	}
	@GetMapping("/test")
	public String test(Model m) {
		return "html/test/test";
	}
}
