package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.Freeboard;
import com.example.demo.vo.GoodsAndAtt;
import com.example.demo.vo.Order;
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
	public List<Order> shop1(String status);
	public List<Order> shop2(String status);
	public List<Order> shop3(String status);
	public List<Order> shop4(String status);
	public List<Order> getshop(String status);
	public Order shopdetail(int ordernum);
	public int shopUpdate(Order findshop);
	public List<GoodsAndAtt> shopitem();
	public GoodsAndAtt getitem(int goodsnum);
	public List<Freeboard> getboardlist(String bname);
	
	public int deletegoods(int goodsnum);
	public int deletegoodsatt(int goodsnum);
}
