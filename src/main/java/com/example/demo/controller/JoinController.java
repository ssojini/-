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
	@GetMapping("/test")
	@ResponseBody
	public String sendTestMail()
	{
	    
	    boolean isSent = us.sendHTMLMessage();
	    
	    return isSent ? "메일 보내기 성공":"메일 보내기 실패";
	}
	   
	 @GetMapping("/auth/{code}")  // 보낸 메일에서 이용자가 인증 링크를 클릭했을 때
	 @ResponseBody
	 public String index(@PathVariable("code")String code,HttpSession session)
	 {
	 String scode = (String)session.getAttribute("cer");
	 if(code.equals(scode))
	 {
		 return "인증완료 코드확인=" + code;
	 }
		 log.info("인증코드 확인={}", code);
		 return "인증실패 인증코드확인="+code;
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
	
}
