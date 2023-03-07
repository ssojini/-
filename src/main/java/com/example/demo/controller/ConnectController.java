package com.example.demo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.ConnectService;

@Controller
@RequestMapping("/connect")
public class ConnectController {
	private static final String URL = "http://localhost:7878";
	@Autowired
	private ConnectService connectService;
	
	@PostMapping("/chatGPT")
	@ResponseBody
	public String chatGPT(String gender, String current_weight, String goal_weight) throws IOException, ParseException {
		Map<String,String> map = new HashMap<>();
		
		map.put("gender", gender);
		map.put("current_weight", current_weight);
		map.put("goal_weight", goal_weight);
		
		String response = connectService.post(URL+"/chatGPT",map);
		System.out.println("response:"+response);
		return response;
	}
}
