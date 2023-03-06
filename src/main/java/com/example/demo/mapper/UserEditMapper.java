package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.Freeboard;
import com.example.demo.vo.Main_Title;
import com.example.demo.vo.User;

@Mapper
public interface UserEditMapper {
	
	public List<User> userlist();
	
	public User userinfo(String userid);
	
	public int useredit(User User);
	
	public int deleteuser(String userid);
	
	public int changepwd(User User);
	
	public List<Freeboard> getmyboard(String nickname);

	public Main_Title maintitle(int i);
	
}
