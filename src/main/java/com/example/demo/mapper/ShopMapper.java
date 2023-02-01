package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.AddGoods_Att;
import com.example.demo.vo.Admin;
import com.example.demo.vo.Goods;
import com.example.demo.vo.GoodsAndAtt;
import com.example.demo.vo.Shop;

@Mapper
public interface ShopMapper {
	
	public Admin admininfo(String adminid);
	
	public int addgoods(Goods goods);
	
	public int addgoods_att(List list);

	public List<Shop> list(String userid);
  
	public Shop detail(String ordernum);
	
	public List<Map<String,Object>> mainpagegoods();
	
	public List<GoodsAndAtt> search(String searchbox);
	
	public List<Map<String,Object>> newproduct();
	public List<Map<String,Object>> randomproduct();
	
	public List<GoodsAndAtt> category1();
	public List<GoodsAndAtt> category2();
	public List<GoodsAndAtt> category3();
}
