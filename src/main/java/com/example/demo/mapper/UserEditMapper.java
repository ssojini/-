package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.UserJoin;


@Mapper
public interface UserEditMapper {
	
	public List<UserJoin> userlist();
	
	public UserJoin userinfo(String userid);
	
	public int useredit(String userid);
	
	public int deleteuser(String userid);
	
}
