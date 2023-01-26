package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.interfaces.UserListRepository;
import com.example.demo.mapper.ManagerMapper;
import com.example.demo.vo.Freeboard;
import com.example.demo.vo.Shop;
import com.example.demo.vo.UserJoin;
import com.example.demo.vo.UserJoinJpa;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ManagerService {
	
	@Autowired
	private ManagerMapper map;
	
	@Autowired
	private UserListRepository repo;
	
	public List<UserJoin> userList()
	{
		return map.userList();
	}
	
	public List<UserJoinJpa> getUserList(Pageable pageable) throws Exception {
        Page<UserJoinJpa> page = repo.findAll(pageable);
        return page.toList();
    }
	
	public UserJoin userdetail(String userid)
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

	public List<Shop> orderlist(String userid) {
		return map.orderlist(userid);
	}

}
