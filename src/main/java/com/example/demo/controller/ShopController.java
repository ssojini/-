package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.tomcat.util.http.fileupload.FileUtils;
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

import com.example.demo.service.ShopService;
import com.example.demo.vo.AddGoods_Att;
import com.example.demo.vo.Goods;
import com.example.demo.vo.UserJoin;
import com.google.gson.JsonObject;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/shop")
@Slf4j
public class ShopController 
{
	@Autowired
	private ShopService svc;
	
	@GetMapping("/mypage")
	public String ShopMyPage() {
		
		return "html/shop/mypage";
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
	

}