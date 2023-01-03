package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.FreeboardService;
import com.example.demo.vo.Freeboard;

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
	private FreeboardService fs;
	
	@GetMapping("/freeboard")
	public String main(Model m) {
		List<Freeboard> freeboardList = fs.getFreeboardList();
		m.addAttribute("freeboardList", freeboardList);
		return "html/freeboard";
	}
	
	private mypageService ms;
	
	@GetMapping("/")
	@ResponseBody
	public String userlist()
	{	
		return ms.userlist().toString();
	}
	
	@GetMapping("/useredit/{userid}") 
	public String addboardform(@PathVariable(value ="userid", required =false) String userid, Model m)
	{	
		m.addAttribute("user", ms.userinfo(userid));
		return "html/EditUser";
	}
	
	@PostMapping("/userEdit")
	@ResponseBody
	public Map<String,Object> useredit(String userid) 
	{
		Map<String,Object> map= new HashMap<>();
		System.out.println("userid: "+ userid);
		System.out.println("ms.useredit(userid):"+ms.useredit(userid));
		map.put("edited",ms.useredit(userid));
		System.out.println("SYSTEM:  "+ms.useredit(userid));
		return map;
		
	}
	
	@GetMapping("/deleteuser/{userid}") 
	public String deleteuser(@PathVariable(value ="userid", required =false) String userid, Model m)
	{	
		m.addAttribute("user", ms.userinfo(userid));
		return "html/DeleteUser";
	}
	
	
	
	
}
