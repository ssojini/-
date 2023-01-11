package com.example.demo.service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.mapper.ShopMapper;
import com.example.demo.vo.AddGoods_Att;
import com.example.demo.vo.Admin;
import com.example.demo.vo.Goods;
import com.example.demo.vo.UserJoin;
import com.example.demo.interfaces.GoodsRepository;
import com.example.demo.vo.Shop;

import lombok.extern.slf4j.Slf4j;


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
  
  /* 현주 */
  
  @Autowired
	private ShopMapper map;
	
	private Path fileStorageLocation;
	
	public Admin admininfo(String adminid)
	{
		return map.admininfo(adminid);
	}

	@Autowired
	  public ShopService(Environment env) 
	  {
	    this.fileStorageLocation = Paths.get("./src/main/resources/static/images/addgoods").toAbsolutePath().normalize();
	    try {
	      Files.createDirectories(this.fileStorageLocation);
	    } catch (Exception ex) {
	      throw new RuntimeException(
	          "Could not create the directory where the uploaded files will be stored.", ex);
	    }
	  }
	
	 private String getFileExtension(String fileName) {
		    if (fileName == null) {
		      return null;
		    }
		    String[] fileNameParts = fileName.split("\\.");

		    return fileNameParts[fileNameParts.length - 1];
		  }

	 public boolean storeFile(MultipartFile file, Goods goods) {
		 // Normalize file name
		 //map.userinfo(userid).getProfile();
		 //System.out.println("fname:  "+  map.userinfo(userjoin.getUserid()).getProfile());
				 
		 String fileName = file.getOriginalFilename();
		        //new Date().getTime() + "-file." + getFileExtension(file.getOriginalFilename());

		    try {
		      // Check if the filename contains invalid characters
		      if (fileName.contains("..")) {
		        throw new RuntimeException(
		            "Sorry! Filename contains invalid path sequence " + fileName);
		      }

		      Path targetLocation = this.fileStorageLocation.resolve(fileName);
		      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

		      //return fileName;
		    } catch (IOException ex) {
		      throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
		    }
		    
		    goods.setMainpic(fileName);
		    int add =  map.addgoods(goods);
			
			boolean added = false;
			if(add>0)
			{
				added =true;
			}
			return added;
		 }
 
}