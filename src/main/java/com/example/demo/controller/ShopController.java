package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.ShopService;
import com.example.demo.vo.FreeBoard;
import com.example.demo.vo.Shop;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/shop")
@Slf4j
public class ShopController 
{
	@Autowired
	private ShopService svc;
	
	@GetMapping("/mypage/{userid}")
	public String ShopMyPage(@PathVariable String userid, Model m) {
		
		m.addAttribute("list", svc.mypagelist(userid));
		m.addAttribute("url","shop/mypage/itemdetail");
		return "html/shop/mypage/mypage";
		
	}
	
	@GetMapping("/mypage/itemdetail/{userid}/{itemid}")
	@ResponseBody
	public String ShopDetail(@PathVariable String userid, @PathVariable Integer itemid) {
		//m.addAttribute("detail", svc.shopDetail(userid, itemid));
		//return "html/shop/mypage/detail";
		log.info("1");
		Shop list = svc.shopDetail(userid, itemid);
		log.info(list.toString());
		return list.toString();
	}
}
