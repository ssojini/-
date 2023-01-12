package com.example.demo.service;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.interfaces.CartRepository;
import com.example.demo.interfaces.GoodsRepository;
import com.example.demo.mapper.ShopMapper;
import com.example.demo.vo.Admin;
import com.example.demo.vo.Cart;
import com.example.demo.vo.Goods;
import com.example.demo.vo.Shop;


@Service
public class ShopService 
{
	/* 상욱 시작 */
	@Autowired
	private GoodsRepository repo;
	@Autowired
	private CartRepository cart_repo;
	
	public Goods getGoods(int goodsnum) 
	{
		Optional<Goods> goods = repo.findById(goodsnum);
		//System.out.println(goods.get());
		return goods.isPresent()?goods.get():null;
	}

	public Map<String, Object> added(Cart cart) 
	{
		Map<String, Object> map = new HashMap<>();
		ArrayList<Cart> cartList = cart_repo.findByUserid(cart.getUserid());
		System.err.println(cartList);
		boolean check_add= false; // 이미 장바구니에 담겼으면 true
		for(int i =0; i<cartList.size();i++)
		{
			if(cart.getGoodsnum()==cartList.get(i).getGoodsnum()) {
				check_add=true;
				break;
			}
		}
		
		if(check_add)
		{
			map.put("added",false);
			map.put("msg","이미 장바구니에 담긴 상품입니다.");	
		}else {		
			Cart added = cart_repo.save(cart);
			if(added!=null) {
				map.put("added",true);
				map.put("msg","장바구니 담기 성공");
			}else {
				map.put("added",false);
				map.put("msg","장바구니 담기 실패");			
			}
		}
		return map;
	}

	public ArrayList<Cart> getCart(String userid) 
	{
		ArrayList<Cart> cartlist = cart_repo.findByUserid(userid);
		return cartlist;
	}
	/* 상욱 끝 */
	
	
	/* 종빈 */
	@Autowired
	private ShopMapper mapper;
	
	public List<Shop> mypagelist(String userid) {
		return mapper.list(userid);
	}
	
	public Shop shopDetail(String ordernum){
		return mapper.detail(ordernum);
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