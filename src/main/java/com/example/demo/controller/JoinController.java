package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.interfaces.JoinRepository;
import com.example.demo.vo.User;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/team")
public class JoinController 
{	
	@Autowired
	private JoinRepository repo;
	
	@GetMapping("/join1")
	public String showjoinForm1()
	{
		return "html/thymeleaf/userJoin1";
	}
	@GetMapping("/join2")
	public String showjoinForm2()
	{
		return "html/thymeleaf/userJoin2";
	}
	@PostMapping("/join")
	@ResponseBody
	public Map<String,Object> join(User user)
	{
		log.info("1");
		Map<String,Object> map = new HashMap<>();
		log.info(user.toString());
		map.put("join", repo.save(user));
		return map;
	}
	
	
}
