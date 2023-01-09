package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.ShopMapper;
import com.example.demo.vo.Admin;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShopService {
	
	@Autowired
	private ShopMapper map;
	
	public Admin admininfo(String adminid)
	{
		return map.admininfo(adminid);
	}
}
