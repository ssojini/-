package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.EatedListService;
import com.example.demo.vo.EatedList;

@Controller
@RequestMapping("/test")
public class TestController {
	@Autowired
	private EatedListService service;
	
	@GetMapping("/eated_list")
	@ResponseBody
	public String eated_list() {
		List<EatedList> list = service.getList();
		return list.toString();
	}
}
