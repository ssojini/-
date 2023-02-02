package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.Freeboard;
import com.example.demo.vo.User;


@Mapper
public interface UserEditMapper {
	
	public List<User> userlist();
	
	public User userinfo(String userid);
	
	public int useredit(User userjoin);
	
	public int deleteuser(String userid);
	
	public int changepwd(User userjoin);
	
	public List<Freeboard> getmyboard(String userid);
	
}
