package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.UserEditMapper;
import com.example.demo.vo.UserJoin;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class mypageService {
	@Autowired
	private UserEditMapper map;
	
	public List<UserJoin> userlist()
	{
		return map.userlist();
	}
	
	public UserJoin userinfo(String userid)
	{
		return map.userinfo(userid);
	}
	
	public boolean useredit(UserJoin userjoin)
	{
		int edit =  map.useredit(userjoin);
		System.out.println("edit: " + edit);
		boolean editted = false;
		if(edit>0)
		{
			editted =true;
		}
		System.out.println("editted:  "+ editted);
		return editted;
	}
	
	public boolean deleteuser(String userid, String pwd)
	{
		UserJoin join = map.userinfo(userid);
		boolean deleted=false;
		if(join.getPwd().equals(pwd))
		{
			int delete = map.deleteuser(userid);
			if(delete>0)
			{
				deleted=true;
			}
		}
		return deleted;
	}
}
