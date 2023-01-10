package com.example.demo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.interfaces.GoodsRepository;
import com.example.demo.service.ShopService;
import com.example.demo.vo.Goods;
import com.example.demo.vo.FreeBoard;
import com.example.demo.vo.Shop;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/shop")
@Slf4j
public class ShopController 
{
	@Autowired
	private ShopService svc;
	@Autowired
	private GoodsRepository repo;
	
	@GetMapping("/mypage/{userid}")
	public String ShopMyPage(@PathVariable String userid, Model m) {
		
		m.addAttribute("list", svc.mypagelist(userid));
		m.addAttribute("url","shop/mypage/itemdetail");
		return "html/shop/mypage/mypage";
		
	}
	
	/*--------------------- 상욱 ----------------------*/
	
	
	@GetMapping("/detail/{goodsnum}")	
	public String detail(@PathVariable("goodsnum") int goodsnum, Model m)
	{
		//System.err.println(goodsnum);
		Goods goods = svc.getGoods(goodsnum);
		m.addAttribute("goods",goods);
		return "html/shop/goodsDetail";
	}	
	//초기 테스트용
	@GetMapping("/")	
	@ResponseBody
	public String index()
	{
		return "Shop Index";
	}
	//초기 테스트 데이터 생성 메소드
	@GetMapping("/add")	
	@ResponseBody
	public String testAdd()
	{
		Goods goods = new Goods(1,"영양제","/images/goods.png",5000,"건강에 좋음","상품에 관한 상세설명","","카테고리1");
		Goods added = repo.save(goods);
		return added.toString();
	}
	
	/* 종빈 */
	@GetMapping("/mypage/itemdetail/{userid}/{itemid}")
	@ResponseBody
	public String ShopDetail(@PathVariable String userid, @PathVariable Integer itemid) {
		//m.addAttribute("detail", svc.shopDetail(userid, itemid));
		//return "html/shop/mypage/detail";
		log.info("1");
		Shop list = svc.shopDetail(userid, itemid);
		log.info(list.toString());
		return list.toString();
	}
}