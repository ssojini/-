package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.Shop;

@Mapper
public interface ShopMapper {
	public List<Shop> list(String userid);
	public Shop detail(String ordernum);
}
