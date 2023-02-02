package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.Freeboard;
import com.example.demo.vo.GoodsAndAtt;
import com.example.demo.vo.Shop;
import com.example.demo.vo.User;


@Mapper
public interface ManagerMapper {
	public List<User> findUser();
	public List<User> userList();
	public User	userDetail(String userid);
	public List<Freeboard> userBoard(String userid);
	public Freeboard boardDetail(int fbnum);
	public int useridDel(String userid);
	public List<Shop> orderlist(String userid);
	public List<Shop> shop1(String status);
	public List<Shop> shop2(String status);
	public List<Shop> shop3(String status);
	public List<Shop> shop4(String status);
	public List<Shop> getshop(String status);
	public Shop shopdetail(int ordernum);
	public int shopUpdate(Shop findshop);
	public List<GoodsAndAtt> shopitem();
}
