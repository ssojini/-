package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.Admin;

@Mapper
public interface ShopMapper {
	
	public Admin admininfo(String adminid);
	
}
