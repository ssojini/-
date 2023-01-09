package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.ShopService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/shop")
@Slf4j
public class ShopController 
{
	@Autowired
	private ShopService svc;
	
	@GetMapping("/mypage/{userid}")
	public String ShopMyPage(@PathVariable(value = "userid")String userid, Model m) {
		
		m.addAttribute("user", svc.mypagelist(userid));
		return "html/shop/mypage";
	}
}
