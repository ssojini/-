package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.ShopService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/shop")
@Slf4j
public class ShopController 
{
	@Autowired
	private ShopService svc;
	
	@GetMapping("/mypage")
	public String ShopMyPage() {
		
		return "html/shop/mypage";
	}
	
	
}