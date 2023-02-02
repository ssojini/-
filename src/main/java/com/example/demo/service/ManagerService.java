package com.example.demo.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.interfaces.ShopListRepository;
import com.example.demo.interfaces.UserListRepository;
import com.example.demo.mapper.ManagerMapper;
import com.example.demo.vo.Freeboard;
import com.example.demo.vo.Shop;
import com.example.demo.vo.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ManagerService {
	
	@Autowired
	private ManagerMapper map;
	
	@Autowired
	private UserListRepository urepo;
	
	@Autowired
	private ShopListRepository srepo;
	
	public List<User> userList()
	{
		return map.userList();
	}
	

	public Page<User> getUserList(Pageable pageable) throws Exception {
        Page<User> page = urepo.findAll(pageable);
        return page; 

    }
	
	public User userdetail(String userid)
	{
		return map.userDetail(userid);
	}
	
	public List<Freeboard> userboard(String userid)
	{
		return map.userBoard(userid);
	}

	public Freeboard boarddetail(int fbnum) {
		return map.boardDetail(fbnum);
	}

	public boolean DelAccount(String userid) {
		int a = map.useridDel(userid);
		
		boolean result = false;
		if(a>0) result=true;
		return result;
	}

	public List<Shop> shop1() {
		String status = "상품준비중";
		return map.shop1(status);
	}
	public List<Shop> shop2() {
		String status = "배송준비중";
		return map.shop2(status);
	}
	public List<Shop> shop3() {
		String status = "배송중";
		return map.shop3(status);
	}
	public List<Shop> shop4() {
		String status = "배송완료";
		return map.shop4(status);
	}

	public Page<Shop> getshop(String status, Pageable pageable) {
		Page<Shop> page = srepo.findBystatus(pageable, status);
		return page;
	}

	public Shop shopdetail(int ordernum) {
		return map.shopdetail(ordernum);
	}

	public int update(Shop shop) {
		Shop findshop = map.shopdetail(shop.getOrdernum());
		findshop.setStatus(shop.getStatus());	
		int updateshop = map.shopUpdate(findshop);
		return updateshop;
	}

}
