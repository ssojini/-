package com.example.demo.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.interfaces.JoinRepository;
import com.example.demo.service.UserService;
import com.example.demo.vo.User;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/team")
public class JoinController 
{	
	@Autowired
	private JoinRepository repo;
	@Autowired
	public HttpSession session;
	@Autowired
	public UserService svc;
	
	
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
	@PostMapping("/idcheck")
	@ResponseBody
	public int idCheck(@RequestParam("userid")User user, Model m)
	{
		Optional<User> op = repo.findById(user.getUserid());
		int cnt = 0;
		m.addAttribute("cnt" ,op.get().getUserid());
		return cnt;
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
	
	/*----------------- [상욱] ----------------- */
	
	@GetMapping("/add")
	@ResponseBody
	public String add()
	{
		Date date = Date.valueOf("2022-12-31");
		User member = new User("smith","1234","clinamen",date,"010-1234-5678","smith@ezen.com","testID");
		User added = repo.save(member);
		return added.toString();
	}
	@GetMapping({"/","/login"})
	public String login()
	{
		return "html/login/login";
	}
	@PostMapping("/login")
	@ResponseBody
	public Map<String,Object> loginProc(User user)
	{					
		return svc.login(user.getUserid(),user.getPwd());
	}
	@GetMapping("/findLoginInfo")
	public String findLoginInfo()
	{
		return "html/login/findLoginInfo";
	}
	@GetMapping("/logon")
	public String logon()
	{
		return "html/login/logon";
	}
	@PostMapping("/check")
	@ResponseBody
	public Map<String,Object> check(User user)
	{
		return svc.check(user.getUserid(),user.getEmail());
	}
	@PostMapping("/reset")
	@ResponseBody
	public Map<String,Object> reset(User user)
	{
		//System.err.println(user);
		return svc.reset(user.getUserid(),user.getPwd());
	}
	@PostMapping("/find")
	@ResponseBody
	public Map<String,Object> find(User user)
	{
		return svc.find(user.getPhone(),user.getEmail());
	}
	@GetMapping("/logout")
	public String logout()
	{
		session.setAttribute("userid", null);
		return "html/login/login";
	}
	
}
