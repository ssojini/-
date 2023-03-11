package com.example.demo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.ConnectService;

@Controller
@RequestMapping("/connect")
public class ConnectController {
	@Autowired
	private ConnectService connectService;
	
	@PostMapping("/chatGPT")
	@ResponseBody
	public String chatGPT(String gender, String current_weight, String goal_weight) throws IOException, ParseException {
		Map<String,String> map = new HashMap<>();
		
		map.put("gender", gender);
		map.put("current_weight", current_weight);
		map.put("goal_weight", goal_weight);
		
		String response = connectService.post("/chatGPT",map);
		System.out.println("response:"+response);

		return response;
	}
	
	@PostMapping("/prod_recommend")
	@ResponseBody
	public String prod_recommand(String userid) throws IOException, ParseException {
		Map<String,String> map = new HashMap<>();
		
		map.put("userid", userid);
				
		String response = connectService.post("/prod_recommend",map);
		System.out.println("Flask response:"+response);
		return response;
	}
	
	@GetMapping("/meal_calc")
	@ResponseBody
	public String meal_calc() throws IOException, ParseException {
		return connectService.meal_calc();
	}
	
	@PostMapping("/food_ident")
	public String food_ident() {
		
		return null;
	}
}
