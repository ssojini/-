package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.FreeboardService;
import com.example.demo.service.ManagerService;
import com.example.demo.vo.Shop;
import com.example.demo.vo.User;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/manager")
@Slf4j
public class ManagerController {
	
	@Autowired
	private ManagerService svc;
	@Autowired
	private FreeboardService freeboardService;
	
	@GetMapping("/")
	public String managermain()
	{
		return "html/manager/managerLogin";
	}
	
	@GetMapping("/userlist")
	public String userList(Model m, @PageableDefault(size=10,sort="userid", page=0) Pageable pageable) throws Exception
	{
		Page<User> list = svc.getUserList(pageable);
		m.addAttribute("userList",list);
		m.addAttribute("url", "manager/userdetail");
		return "html/manager/userList";
	}
	
	@GetMapping("/userdetail/{userid}")
	public String userdetail(@PathVariable String userid, Model m)
	{
		m.addAttribute("userdetail",svc.userdetail(userid));
		m.addAttribute("url", "manager/userboard/detail");
		return "html/manager/userdetail";
	}
	
	@GetMapping("/userboard/detail/{fbnum}")
	public String boarddetail(@PathVariable int fbnum, Model m) 
	{
		m.addAttribute("boarddetail",svc.boarddetail(fbnum));
		return "html/manager/boardDetail";
	}
	
	@PostMapping("/DelAccount")
	@ResponseBody
	public Map<String, Object> DelAccount(String userid, Model m)
	{
		Map<String,Object> map = new HashMap<>();
		map.put("result",svc.DelAccount(userid));
		return map;
	}
	
	@GetMapping("/shop")
	public String shop(Model m)
	{
		m.addAttribute("shop1", svc.shop1());
		m.addAttribute("shop2", svc.shop2());
		m.addAttribute("shop3", svc.shop3());
		m.addAttribute("shop4", svc.shop4());
		m.addAttribute("url", "manager/shoplist");
		return "html/manager/shop";
	}
	
	@GetMapping("/shoplist")
	public String shoplist(@RequestParam String status,@PageableDefault(size=10,sort="ordernum", page=0) Pageable pageable, Model m)
	{
		Page<Shop> list = svc.getshop(status, pageable);
		m.addAttribute("status", status);
		m.addAttribute("url", "manager/shop/detail");
		m.addAttribute("shop", list);
		return "html/manager/shoplist";
	}
	
	@GetMapping("/shop/detail")
	public String shopdetail(@RequestParam int ordernum, Model m)
	{
		m.addAttribute("shop",svc.shopdetail(ordernum));
		return "html/manager/shopdetail";
	}
	
	@PostMapping("/shop/edit")
	@ResponseBody
	public Map<String,Object> shopedit(int ordernum, String status) 
	{
		System.out.println("shop ordernum : "+ordernum);
		System.out.println("shop Status : "+status);
		Map<String,Object> map = new HashMap<>();
		Shop shop = new Shop();
		shop.setOrdernum(ordernum);
		shop.setStatus(status);
		svc.update(shop);
		
		map.put("result", true);
		return map;
	}
	
	@GetMapping("/board")
	public String boardList(Model m, Pageable page, String title)
	{
		System.out.println("title:"+title);
		m.addAttribute("listFreeboard",freeboardService.getListByBnameAndTitle(title, "", page));
		return "html/manager/boardList";
	}
	
	@GetMapping("/shopitem")
	public String shopitem(Model m)
	{
		m.addAttribute("shopitem",svc.shopitem());
		return "html/manager/shopitem";
	}
}	
