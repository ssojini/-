package com.example.demo.controller;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import com.example.demo.interfaces.GoodsRepository;
import com.example.demo.service.ShopService;
import com.example.demo.vo.AddGoods_Att;
import com.example.demo.vo.Goods;
import com.example.demo.vo.UserJoin;
import com.google.gson.JsonObject;
import com.example.demo.vo.Goods;
import com.example.demo.vo.FreeBoard;
import com.example.demo.vo.Shop;
import jakarta.servlet.http.HttpServletRequest;
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
		Goods goods = new Goods(1,"영양제","/images/goods.png",5000,"건강에 좋음","상품에 관한 상세설명","","카테고리1","");
		Goods added = repo.save(goods);
		return added.toString();
	}
	

	@GetMapping("/addgoods/{adminid}")
	public String addgoods(@PathVariable(value = "adminid", required = false) String adminid, Model m)
	{
		m.addAttribute("admin",svc.admininfo(adminid));
		return "html/shop/AddGoods";
	}
	

	@PostMapping("/addgoods")
	@ResponseBody
	public Map<String,Object> add(@RequestParam("file")MultipartFile mfiles, 
			HttpServletRequest request, Goods goods)
	{
		Map<String,Object> map = new HashMap<>();
		map.put("added", svc.storeFile(mfiles,goods));
		return map;
	}

	/* 종빈 */
	
	@GetMapping("/mypage/{userid}")
	public String ShopMyPage(@PathVariable String userid, Model m) {
		m.addAttribute("list", svc.mypagelist(userid));
		m.addAttribute("url","shop/mypage/itemdetail");
		return "html/shop/mypage/mypage";
		
	}
	
	@GetMapping("/mypage/itemdetail/{userid}/{itemid}")
	@ResponseBody
	public String ShopDetail(@PathVariable String userid, @PathVariable Integer itemid) {
		//m.addAttribute("detail", svc.shopDetail(userid, itemid));
		//return "html/shop/mypage/detail";
		Shop list = svc.shopDetail(userid, itemid);
		return list.toString();
	}

}