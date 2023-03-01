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

import com.example.demo.HttpSessionHandler;
import com.example.demo.interfaces.UserRepository;
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
	private UserRepository repo;
	@Autowired
	public HttpSession session;
	@Autowired
	public UserService us;
	@Autowired
	public EmailService es;
	
	
	//초기 데이터 생성 메소드
	@GetMapping("/add")
	@ResponseBody
	public String add(HttpSession session)
	{
		//상욱
		Date date = Date.valueOf("2022-12-31");
		User member = new User("asdf","1234","clinamen",date,"010-1234-5678","siesta_w@naver.com","/profile/default.png");
		User added = repo.save(member);
		
		// 현주 

		return added.toString();
	}
	//이용약관
	@GetMapping("/rules")
	public String showjoinForm1()
	{
		return "html/join/rules";
	}
	//회원가입폼
	@GetMapping("/joinForm")
	public String showjoinForm2()
	{
		return "html/join/userJoin";
	}
	//중복확인
	@PostMapping("/idcheck")
	@ResponseBody
	public Map<String,Object> idCheck(User user)
	{
		Map<String,Object> map = new HashMap<>();
		Optional<User> op = repo.findById(user.getUserid());
		map.put("idcheck", op.isPresent()?true:false);
		return map;
	}
	@PostMapping("/nickcheck")
	@ResponseBody
	public Map<String, Object> nickCheck(User user)
	{
		Map<String,Object> map = new HashMap<>();
		User nick = repo.findByNickname(user.getNickname());
		map.put("nickcheck", nick);
		
		return map;
	}
	//가입
	@PostMapping("/join")
	@ResponseBody
	public Map<String,Object> join(User user)
	{
		Map<String,Object> map = new HashMap<>();
		map.put("join", repo.save(user));
		return map;
	}
	//이메일인증
	@PostMapping("/sendemail")
	@ResponseBody
	public Map<String,Object> sendEamil(String email)
	{
		return us.sendEmail(email);
	}
	
	/*----------------- [상욱 시작] ----------------- */
	
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
		return us.check(user.getUserid(),user.getEmail());
	}
	@PostMapping("/reset")
	@ResponseBody
	public Map<String,Object> reset(User user)
	{
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
	@GetMapping("/auth/{sid}/{rdStr}")
	@ResponseBody
	public String authCheck(@PathVariable("rdStr")String rdStrCheck,
							@PathVariable("sid") String sid)
	{		
		log.info("sid:"+sid);
		log.info("rdStrcon:"+rdStrCheck);
		
		System.out.println("HttpSessionHandler.map:"+HttpSessionHandler.map);
		HttpSession orgSession = HttpSessionHandler.map.get(sid);
		System.err.println("original: "+orgSession);
		
		if(rdStrCheck.equals(orgSession.getAttribute("rdStr")))

		{
			orgSession.setAttribute("authCheck", "1");
			return "인증완료";
		}
		
		orgSession.setAttribute("authCheck", "0");
		return "인증실패";
	}
	
	//
	@PostMapping("/authEmail")
	@ResponseBody
	public boolean authorizedEmail()	
	{
		if(session.getAttribute("authCheck")==""||session.getAttribute("authCheck")==null) {
			session.setAttribute("authCheck", "0");
		}
		String authCheck = (String) session.getAttribute("authCheck");
		
		if(authCheck.equals("1")) return true;
		
		return false;
	}
	
	/*----------------- [상욱 끝] ----------------- */
	
}
