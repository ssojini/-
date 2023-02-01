package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.Freeboard;
import com.example.demo.vo.UserJoin;


@Mapper
public interface UserEditMapper {
	
	public List<UserJoin> userlist();
	
	public UserJoin userinfo(String userid);
	
	public int useredit(UserJoin userjoin);
	
	public int deleteuser(String userid);
	
	public int changepwd(UserJoin userjoin);
	
	public List<Freeboard> getmyboard(String userid);
	
}
