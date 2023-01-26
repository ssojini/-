package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;

import com.example.demo.vo.Freeboard;
import com.example.demo.vo.Shop;
import com.example.demo.vo.UserJoin;

@Mapper
public interface ManagerMapper {
	public List<UserJoin> findUser();
	public List<UserJoin> userList();
	public UserJoin	userDetail(String userid);
	public List<Freeboard> userBoard(String userid);
	public Freeboard boardDetail(int fbnum);
	public int useridDel(String userid);
	public List<Shop> orderlist(String userid);
}
