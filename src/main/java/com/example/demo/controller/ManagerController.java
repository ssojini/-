package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.ManagerService;
import com.example.demo.vo.UserJoin;
import com.example.demo.vo.UserJoinJpa;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/manager")
@Slf4j
public class ManagerController {
	
	@Autowired
	private ManagerService svc;
	
	@GetMapping("/")
	public String managermain()
	{
		return "html/manager/ManagerMain";
	}
	
	@GetMapping("/userlist")
	public String userList(Model m, @PageableDefault(size=10) Pageable pageable) throws Exception
	{
		List<UserJoinJpa> list = svc.getUserList(pageable);
		m.addAttribute("userList",list);
		m.addAttribute("url", "manager/userdetail");
		return "html/manager/userList";
	}
	
	@GetMapping("/userdetail/{userid}")
	public String userdetail(@PathVariable String userid, Model m)
	{
		m.addAttribute("userdetail",svc.userdetail(userid));
		m.addAttribute("userboard", svc.userboard(userid));
		m.addAttribute("orderlist",svc.orderlist(userid));
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
		log.info(userid);
		Map<String,Object> map = new HashMap<>();
		map.put("result",svc.DelAccount(userid));
		return map;
	}
}	
