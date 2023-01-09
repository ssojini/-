package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.UserJoin;


@Mapper
public interface UserEditMapper {
	
	public List<UserJoin> userlist();
	
	public UserJoin userinfo(String userid);
	
	public int useredit(UserJoin userjoin);
	
	public int deleteuser(String userid);
	
<<<<<<< HEAD
	public int changepwd(UserJoin userjoin);
=======
	public int changepwd(String userid);
>>>>>>> branch 'master' of https://github.com/pastebean/EzenFinal.git
	
}
