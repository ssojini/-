package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.mypageService;
import com.example.demo.vo.UserJoin;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/health")
@Slf4j
public class healthController {
	@Autowired
	private mypageService svc;
	
	@GetMapping("/")
	@ResponseBody
	public String userlist()
	{	
		return svc.userlist().toString();
		
		//return svc.userinfo(userid).toString();
	}
	
	@GetMapping("/useredit/{userid}") 
	public String addboardform(@PathVariable(value ="userid", required =false) String userid, Model m)
	{	
		m.addAttribute("user", svc.userinfo(userid));
		return "html/EditUser";
	}
	
	@PostMapping("/userEdit")
	@ResponseBody
	public Map<String,Object> useredit(UserJoin userjoin) 
	{
		Map<String,Object> map= new HashMap<>();
		//System.out.println("userid: "+ userid);
		map.put("edited",svc.useredit(userjoin));
		System.out.println("SYSTEM:  "+svc.useredit(userjoin));
		return map;
		
	}
	
	@GetMapping("/deleteuser/{userid}") 
	public String deleteuser(@PathVariable(value ="userid", required =false) String userid, Model m)
	{	
		m.addAttribute("user", svc.userinfo(userid));
		return "html/DeleteUser";
	}
	
	
	
	
}
