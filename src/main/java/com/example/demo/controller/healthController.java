package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.FreeboardService;

import ch.qos.logback.core.model.Model;

@Controller
@RequestMapping("/health")
public class healthController {
	@Autowired
	private FreeboardService svc;
	
	@GetMapping("/freeboard")
	public String main(Model m) {
		return "html/freeboard";
	}
}
