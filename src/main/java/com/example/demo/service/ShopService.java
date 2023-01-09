package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.ShopMapper;
import com.example.demo.vo.Shop;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShopService 
{
	@Autowired
	private ShopMapper mapper;
	
	public List<Shop> mypagelist(String userid) {
	
		return mapper.list(userid);
	}

}