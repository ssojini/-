package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.FreeboardService;
import com.example.demo.vo.Freeboard;

@Controller
@RequestMapping("/health")
public class healthController {
	@Autowired
	private FreeboardService svc;
	
	@GetMapping("/freeboard")
	public String main(Model m) {
		List<Freeboard> freeboardList = svc.getFreeboardList();
		m.addAttribute("freeboardList", freeboardList);
		return "html/freeboard";
	}
}
