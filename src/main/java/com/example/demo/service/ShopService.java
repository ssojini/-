package com.example.demo.service;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.interfaces.GoodsRepository;
import com.example.demo.mapper.ShopMapper;
import com.example.demo.vo.Goods;
import com.example.demo.vo.Shop;


@Service
public class ShopService 
{
	/* 상욱 */
	@Autowired
	private GoodsRepository repo;
	
	public Goods getGoods(int goodsnum) 
	{
		Optional<Goods> goods = repo.findById(goodsnum);
		//System.out.println(goods.get());
		return goods.isPresent()?goods.get():null;
	}
	/* 종빈 */
	@Autowired
	private ShopMapper mapper;
	
	public List<Shop> mypagelist(String userid) {
	
		return mapper.list(userid);
	}
	
	public Shop shopDetail(String userid, int itemid){
		return mapper.detail(userid,itemid);
	}
 
}