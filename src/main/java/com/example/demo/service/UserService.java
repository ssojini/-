package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.interfaces.UserRepository;
import com.example.demo.vo.User;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService implements UserDetailsService
{
	//	@Autowired
	//	private JavaMailSender sender;
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

			// 닉네임도 같이 세션에 저장
			session.setAttribute("nickname", user.getNickname());
		}else {
			map.put("login",false);			
			map.put("msg","로그인 실패");
			session.setAttribute("userid", null);			
		}
		return map;
	}
	public Map<String, Object> check(String userid, String email) 
	{
		//

		Map<String,Object> map = new HashMap<>();
		session.setAttribute("rdStr", "");
		session.setAttribute("authCheck", "0");
		User user = repo.findByUseridAndEmail(userid,email);
		if(user!=null) {
			map.put("userid", user.getUserid());
			//System.err.println(user.getUserid());
			session.setAttribute("email", email);		

			// 이메일 발송			
			map.put("msg", esvc.checkmail(session)?"메일발송":"메일발송실패");
			String rdStr = (String) session.getAttribute("rdStr");
			session.setAttribute("rdStr", rdStr);
			map.put("rdStr",rdStr);
			map.put("checked", "send email");
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

	//소진
	public Map<String, Object> sendEmail(String email) 
	{
		Map<String,Object> map = new HashMap<>();

		session.setAttribute("rdStr", "");
		session.setAttribute("authCheck", "0");

		session.setAttribute("email", email);

		if(!(email==null))
		{	
			map.put("msg", esvc.checkmail(session)?"메일발송":"메일발송실패");
			String rdStr = (String) session.getAttribute("rdStr");
			session.setAttribute("rdStr", rdStr);

			log.info("svc.rdStr:"+rdStr);

			map.put("rdStr",rdStr);
			map.put("checked", "send email");
		}
		return map;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User activeUserInfo = repo.findByUserid(username);
		GrantedAuthority authority = new SimpleGrantedAuthority(activeUserInfo.getRole());
		UserDetails userDetails = (UserDetails)new org.springframework.security.core.userdetails.User(activeUserInfo.getUserid(),activeUserInfo.getPwd(),Arrays.asList(authority));
		return userDetails;
	}
}