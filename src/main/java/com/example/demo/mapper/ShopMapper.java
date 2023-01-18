package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.AddGoods_Att;
import com.example.demo.vo.Admin;
import com.example.demo.vo.Goods;
import com.example.demo.vo.Shop;

@Mapper
public interface ShopMapper {
	
	public Admin admininfo(String adminid);
	
	public int addgoods(Goods goods);
	
	public int addgoods_att(List list);

	public List<Shop> list(String userid);
  
	public Shop detail(String ordernum);
	
	public List<Map<String,Object>> mainpagegoods();
}
