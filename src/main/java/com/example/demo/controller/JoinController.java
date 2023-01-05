package com.example.demo.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.interfaces.JoinRepository;
import com.example.demo.service.EmailService;
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
	public UserService us;
	@Autowired
	public EmailService es;
	
	
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
	public Map<String,Object> idCheck(User user)
	{
		Map<String,Object> map = new HashMap<>();
		Optional<User> op = repo.findById(user.getUserid());
		log.info("1:"+op);
		map.put("idcheck", op.isPresent()?true:false);
		return map;
	}
	@PostMapping("/join")
	@ResponseBody
	public Map<String,Object> join(User user)
	{
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
		User member = new User("asdf","1234","clinamen",date,"010-1234-5678","asdf@asdf.com","testID");
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
		return us.login(user.getUserid(),user.getPwd());
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
		//System.err.println("useremail: "+user.getEmail());
		return us.check(user.getUserid(),user.getEmail());
	}
	@PostMapping("/reset")
	@ResponseBody
	public Map<String,Object> reset(User user)
	{
		//System.err.println(user);
		return us.reset(user.getUserid(),user.getPwd());
	}
	@PostMapping("/find")
	@ResponseBody
	public Map<String,Object> find(User user)
	{
		return us.find(user.getPhone(),user.getEmail());
	}
	@GetMapping("/logout")
	public String logout()
	{
		session.setAttribute("userid", null);
		return "html/login/login";
	}
	
	//---------------이메일 인증------------------
	
	//이메일에서 인증버튼 눌러서 인증을 한다.
	@GetMapping("/auth/{rdStr}")
	@ResponseBody
	public String authCheck(@PathVariable("rdStr")String rdStrCheck)
	{		
		
		if(rdStrCheck.equals(session.getAttribute("rdStr")))
		{
			session.setAttribute("authCheck", "1");
			return "인증완료";
		}
		
		session.setAttribute("authCheck", "0");
		return "인증실패";
	}
	
	//
	@PostMapping("/authEmail")
	@ResponseBody
	public boolean authorizedEmail()	
	{
		//System.err.println("here");		
		if(session.getAttribute("authCheck")==""||session.getAttribute("authCheck")==null) {
			session.setAttribute("authCheck", "0");
		}
		//System.err.println(session.getAttribute("authCheck"));
		String authCheck = (String) session.getAttribute("authCheck");
		//System.err.println("string: "+authCheck);
		
		if(authCheck.equals("1")) return true;
		
		return false;
	}
	
}
