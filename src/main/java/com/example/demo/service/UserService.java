package com.example.demo.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.interfaces.UserRepository;
import com.example.demo.vo.User;


import jakarta.servlet.http.HttpSession;

@Service
public class UserService 
{

	@Autowired
	public HttpSession session;
	@Autowired
	private UserRepository repo;
	@Autowired
	private EmailService esvc;
	
	
	public Map<String, Object> login(String userid, String pwd) {
		Map<String,Object> map = new HashMap<>();
		User user = repo.findByUseridAndPwd(userid,pwd);
		if(user!=null) {
			map.put("user",user);
			map.put("login",true);			
			map.put("msg","로그인 성공");			
			session.setAttribute("userid", user.getUserid());
	
		}else {
			map.put("login",false);			
			map.put("msg","로그인 실패");
			session.setAttribute("userid", null);			
		}
		return map;
	}
	public Map<String, Object> check(String userid, String email) 
	{
		Map<String,Object> map = new HashMap<>();
		User user = repo.findByUseridAndEmail(userid,email);
		if(user!=null) {
			map.put("userid", user.getUserid());
			//System.err.println(user.getUserid());
			session.setAttribute("email", email);		
									
			// 이메일 발송			
			map.put("msg", esvc.checkmail(session));
			map.put("checked", true);
		}else {
			map.put("checked", false);
			map.put("userid", null);
			map.put("msg", "No Account");
		}
		return map;
	}
	public Map<String, Object> reset(String userid, String pwd) 
	{	
		Map<String, Object> map = new HashMap<>();
		Optional<User> user =repo.findById(userid);
		user.get().setPwd(pwd);
		if(repo.save(user.get())!=null)
		{
			map.put("msg", "비밀번호 변경 성공!");
			map.put("reset", true);
		}else {
			map.put("msg", "변경 실패!");
			map.put("reset", false);			
		}
		return map;
	}
	public Map<String, Object> find(String phone, String email) 
	{
		Map<String,Object> map = new HashMap<>();
		User user = repo.findByPhoneAndEmail(phone,email);
		if(user!=null) {
			map.put("userid", user.getUserid());
			map.put("found", true);
		}else {
			map.put("userid", null);
			map.put("found", false);			
		}
		return map;
	}

		
	
	
}