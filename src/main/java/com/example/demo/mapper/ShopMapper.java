package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.Admin;
import com.example.demo.vo.Goods;

@Mapper
public interface ShopMapper {
	
	public Admin admininfo(String adminid);
	
	public int addgoods(Goods goods);
	
	public int addgoods_att(List list);
}
