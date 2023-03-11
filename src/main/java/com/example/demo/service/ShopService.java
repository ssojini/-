package com.example.demo.service;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.mapper.ShopMapper;
import com.example.demo.repository.AddGoods_AttReopsitory;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.GoodsRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.vo.AddGoods_Att;
import com.example.demo.vo.Admin;
import com.example.demo.vo.Cart;
import com.example.demo.vo.Goods;
import com.example.demo.vo.GoodsAndAtt;
import com.example.demo.vo.Order;
import com.example.demo.vo.Shop;
import com.google.gson.JsonObject;

import jakarta.servlet.http.HttpServletRequest;


@Service
public class ShopService 
{
	@Autowired //상품
	private GoodsRepository goods_repo;
	
	@Autowired //상품 디테일
	private AddGoods_AttReopsitory attGoods_repo;
	
	@Autowired //장바구니
	private CartRepository cart_repo;
	
	@Autowired //구매
	private OrderRepository order_repo;
	
	@Autowired
	private ConnectService con_svc;

	
	
	/* 상욱 시작 */
	/* 상품 상세보기 시작*/
	// 상품찾기
	public Goods getGoods(int goodsnum) 
	{
		Optional<Goods> goods = goods_repo.findById(goodsnum);
		return goods.isPresent()?goods.get():null;
	}
	public ArrayList<AddGoods_Att> getAddGoodsAtt(int goodsnum) {
		
		
		ArrayList<AddGoods_Att> list = attGoods_repo.findByGoodsnum(goodsnum);		
		
		return list;
	}

	/*상품 상세보기 끝*/
	
	/*장바구니 시작*/
	//장바구니 담기
	public Map<String, Object> added(Cart cart) 
	{
		Map<String, Object> map = new HashMap<>();
		ArrayList<Cart> cartList = cart_repo.findByUserid(cart.getUserid());
		boolean check_add= false; 
		// 이미 장바구니에 담겼으면 true
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

	// 장바구니 가져오기
	public ArrayList<Cart> getCart(String userid) 
	{
		ArrayList<Cart> cartlist = cart_repo.findByUserid(userid);
		return cartlist;
	}
	
	
	// 장바구니 개별상품 수량 변경
	public Map<String, Object> cnt_change(int cartnum, int prod_cnt) 
	{
		Map<String, Object> map = new HashMap<>();
		Optional<Cart> cart = cart_repo.findById(cartnum);
		
		int sum = cart.get().getPrice()*prod_cnt;
		cart.get().setProd_cnt(prod_cnt);
		cart.get().setSum(sum);
		
		if(cart_repo.save(cart.get())!=null) {
			map.put("msg","수량 변경 성공!");
		} else {
			map.put("msg","수량 변경 실패!");
		}
		return map;
	}
	
	// 장바구니 전체 삭제
	public Map<String, Object> delAll()
	{
		Map<String, Object> map = new HashMap<>();
		cart_repo.deleteAll();
		map.put("msg","장바구니 비우기 성공");
		
		return map;
	}
	
	//장바구니 선택 삭제
	public boolean delSel(HttpServletRequest request) 
	{
		String[] ajaxMsg = request.getParameterValues("valueArr");
		try {
			for(int i=0; i<ajaxMsg.length;i++)
			{
				cart_repo.deleteById(Integer.valueOf(ajaxMsg[i]));			
			}	
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	
	//즉시 구매
	public List<Cart> buyNow(Cart cart) 
	{
		List<Cart> orderlist = new ArrayList<>();
		cart.setSum(cart.getPrice()*cart.getProd_cnt());
		orderlist.add(cart);
		return orderlist;
	}
	
	//장바구니 구매(선택/전체)
	public List<Cart> buyCart(String items) {
		
		List<Cart> orderlist = new ArrayList<>();		
		JSONParser parser = new JSONParser(); 
		try 
		{ 
			JSONArray jsArr= (JSONArray) parser.parse(items);
			for( int i=0; i<jsArr.size();i++) 
			{ 
				JSONObject jsObj= (JSONObject) jsArr.get(i);
				int cartnum = Integer.valueOf((String) jsObj.get("cartnum"));
				String userid = (String) jsObj.get("userid");
				Cart cart = cart_repo.findByCartnumAndUserid(cartnum,userid);
				orderlist.add(cart);
				
			}
		} catch (ParseException e) { 
			e.printStackTrace(); 
			}
		return orderlist;
	}
	
	/*장바구니 끝*/
	
	/* 결제 시작*/
	public boolean payment(String items, String userid, String address) {
		
		JSONParser parser = new JSONParser();
		try {
			JSONArray jsArr= (JSONArray) parser.parse(items);
			for( int i=0; i<jsArr.size();i++) 
			{ 
				Order order = new Order();
				JSONObject jsObj = (JSONObject) jsArr.get(i); 
				
				int goodsnum = Integer.valueOf((String) jsObj.get("goodsnum"));
				String goodsname = (String) jsObj.get("goodsname");
				int price = Integer.valueOf((String) jsObj.get("price"));
				int prod_cnt = Integer.valueOf((String) jsObj.get("prod_cnt"));
				int sum = Integer.valueOf((String) jsObj.get("sum"));				
				String mainpic_server = (String) jsObj.get("mainpic_server");
				int itempoint = (int)(sum*0.03);
				
				order.setUserid(userid);
				order.setGoodsnum(goodsnum);
				order.setGoodsname(goodsname);
				order.setPrice(price);
				order.setProd_cnt(prod_cnt);
				order.setSum(sum);
				order.setItempoint(itempoint);
				order.setMainpic_server(mainpic_server);
				order.setAddress(address);
				order.setStatus("상품준비중");
				Order save_or = order_repo.save(order);
				
				// 구매한 상품 장바구니 삭제
				int cartnum = Integer.valueOf((String) jsObj.get("cartnum"));
				if(cartnum!=0) {
					cart_repo.deleteById(cartnum);
				}
			}
			return true;
		} catch (ParseException e) {			
			e.printStackTrace();
		}
		return false;
	}
	
	/* 결제 끝*/	
	
	/* 상욱 끝 */
	
	
	/* 종빈 */

	  @Autowired
		private ShopMapper map;
	
	public List<Shop> mypagelist(String userid) {
		return map.list(userid);
	}
	
	public Shop shopDetail(String ordernum){
		return map.detail(ordernum);
	}
  
  /* 현주 */
	

	public List<GoodsAndAtt> maingoods()
	{
		List<Map<String,Object>> goodslist = map.mainpagegoods();
		List<GoodsAndAtt> goodslists = new ArrayList<>();
		for(int i=0; i<goodslist.size(); i++)
		{
			Map<String, Object> m = goodslist.get(i);
			
			GoodsAndAtt both = new GoodsAndAtt();

			BigDecimal big = (BigDecimal) m.get("PRICE");
			both.setPrice(big.intValue());  
			BigDecimal big1 = (BigDecimal) m.get("GOODSNUM");
			both.setGoodsnum(big1.intValue());
			both.setGoodsname((String) goodslist.get(i).get("GOODSNAME"));
			both.setCategory((String) goodslist.get(i).get("CATEGORY"));
			both.setGoods_detail((String) goodslist.get(i).get("GOODS_DETAIL"));
			

			BigDecimal big2 = (BigDecimal) m.get("GOODSNUM");
			both.setGoodsnum(big2.intValue());
			both.setMainpic_server((String)goodslist.get(i).get("MAINPIC_SERVER"));
			//goods.getAttlist().add(att);
			goodslists.add(both);
		}
		return goodslists;
	}
	
	public List<GoodsAndAtt> newproduct()
	{
		List<Map<String,Object>> goodslist = map.newproduct();
		List<GoodsAndAtt> newproduct = new ArrayList<>();
		for(int i=0; i<goodslist.size(); i++)
		{
			Map<String, Object> m = goodslist.get(i);
			
			GoodsAndAtt both = new GoodsAndAtt();

			BigDecimal big = (BigDecimal) m.get("PRICE");
			both.setPrice(big.intValue());  
			BigDecimal big1 = (BigDecimal) m.get("GOODSNUM");
			both.setGoodsnum(big1.intValue());
			both.setGoodsname((String) goodslist.get(i).get("GOODSNAME"));
			both.setCategory((String) goodslist.get(i).get("CATEGORY"));
			both.setGoods_detail((String) goodslist.get(i).get("GOODS_DETAIL"));
			

			BigDecimal big2 = (BigDecimal) m.get("GOODSNUM");
			both.setGoodsnum(big2.intValue());
			both.setMainpic_server((String)goodslist.get(i).get("MAINPIC_SERVER"));
			newproduct.add(both);
		}
		return newproduct;
	}
	
	public List<GoodsAndAtt> randomproduct()
	{
		List<Map<String,Object>> goodslist = map.randomproduct();
		List<GoodsAndAtt> randomproduct = new ArrayList<>();
		for(int i=0; i<goodslist.size(); i++)
		{
			Map<String, Object> m = goodslist.get(i);
			
			GoodsAndAtt both = new GoodsAndAtt();

			BigDecimal big = (BigDecimal) m.get("PRICE");
			both.setPrice(big.intValue());  
			BigDecimal big1 = (BigDecimal) m.get("GOODSNUM");
			both.setGoodsnum(big1.intValue());
			both.setGoodsname((String) goodslist.get(i).get("GOODSNAME"));
			both.setCategory((String) goodslist.get(i).get("CATEGORY"));
			both.setGoods_detail((String) goodslist.get(i).get("GOODS_DETAIL"));
			

			BigDecimal big2 = (BigDecimal) m.get("GOODSNUM");
			both.setGoodsnum(big2.intValue());
			both.setMainpic_server((String)goodslist.get(i).get("MAINPIC_SERVER"));
			randomproduct.add(both);
		}
		return randomproduct;
	}
  
	 private final Path fileStorageLocation;

	 @Autowired
	  public ShopService(Environment env) //파일 저장경로설정
	  {
	    this.fileStorageLocation = Paths.get("./src/main/webapp/WEB-INF/files/goodsimg")
	        .toAbsolutePath().normalize();
	    try {
	      Files.createDirectories(this.fileStorageLocation);
	    } catch (Exception ex) {
	      throw new RuntimeException(
	          "Could not create the directory where the uploaded files will be stored.", ex);
	    }
	  }

	public Admin admininfo(String adminid)
	{
		return map.admininfo(adminid);
	}
	
	 public String filesave(MultipartFile file)
	 {
		 System.out.println(file.getOriginalFilename());
		// String savedFileName = filesaves(file);
		 JsonObject json = new JsonObject(); 
		

		 Path fileRoot =  Paths.get("./src/main/webapp/WEB-INF/files/goodsimg") .toAbsolutePath().normalize();

		 String fileRoot2 =  "C:\\summernote_image\\";	
		    String originalFileName = file.getOriginalFilename();	//오리지날 파일명
		    String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); //파일 확장자

		    String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명
		   
		    File targetFile = new File(fileRoot +"/"+ savedFileName);
		    File targetFile2 = new File(fileRoot2 + savedFileName);//summernote priview
		    
		    try {
		        // 파일 저장
		        InputStream fileStream = file.getInputStream();
		        FileUtils.copyInputStreamToFile(fileStream, targetFile);
		        System.out.println(targetFile);
		      
		        InputStream fileStream2 = file.getInputStream(); //summernote priview
		        FileUtils.copyInputStreamToFile(fileStream2, targetFile2);
		        json.addProperty("url", "/summernoteImage/"+savedFileName); 
		        json.addProperty("responseCode", "success");
		   
		    } catch (IOException e) {
		        FileUtils.deleteQuietly(targetFile);	
		        json.addProperty("responseCode", "error");
		        e.printStackTrace();
		    }
	        // 파일을 열기위하여 common/getImg.do 호출 / 파라미터로 savedFileName 보냄.
	        
		   String jsonvalue = json.toString();
		   return jsonvalue;

	 }
	 
	 public boolean storeFile(MultipartFile file, String goods_detail, List<String> fileList,Goods goods, AddGoods_Att att) {

		 	List<AddGoods_Att>list = new ArrayList<>();
		    String fileName= file.getOriginalFilename();
		    
			 String mainpic_original = file.getOriginalFilename();
			 String extension1 = mainpic_original.substring(mainpic_original.lastIndexOf("."));	
			 String mainpic_server = UUID.randomUUID() + extension1;

			 String detail_original = "1"; // detail original filename저장안됨 
			 
			 try {
			      // Check if the filename contains invalid characters
			      if (fileName.contains("..")) {
			        throw new RuntimeException(
			            "Sorry! Filename contains invalid path sequence " + fileName);
			      }
			      Path targetLocation = this.fileStorageLocation.resolve(mainpic_server);
			      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
			        //targetLocation에 file.getInputStream을 카피해서 넣어준다 ,  //만약 사진파일이 존재한다면 덮어씌운다 

			    } catch (IOException ex) {
			      throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
			    }
			 
			 goods.setGoods_detail(goods_detail);
			 int add =  map.addgoods(goods);
			 int addatt = 0;
				//원본 파일경로
				for(int i=0;i<fileList.size();i++){
					
					System.out.println("fileList:  "+ fileList.get(i));
				    att.setDetail_server(fileList.get(i));
				    att.setMainpic_original(mainpic_original);
				    att.setMainpic_server(mainpic_server);
					att.setDetail_original(detail_original);
					 list.add(att);
					 addatt = map.addgoods_att(list);
				}
				boolean added = false;
				if(add>0 && addatt>0)
				{
					added =true;
				}
				return added;
		  }
	 
	 public List<GoodsAndAtt> search(String searchbox){
		 List<GoodsAndAtt> list = map.search(searchbox);
		 return list;
	 }
	 
	 public List<GoodsAndAtt> category1(){
		 List<GoodsAndAtt> list = map.category1();
		 return list;
	 }
	 public List<GoodsAndAtt> category2(){
		 List<GoodsAndAtt> list = map.category2();
		 return list;
	 }
	 public List<GoodsAndAtt> category3(){
		 List<GoodsAndAtt> list = map.category3();
		 return list;
	 }
	 
	 public List<GoodsAndAtt> editGoodspage(int goodsnum){
		 List<GoodsAndAtt> list = map.editGoodspage(goodsnum);
		 return list;
	 }
	 
	 public boolean editgoods()
	 {
		 return false;
	 }
	public List<GoodsAndAtt> recommand(Map<String, String> map1)  
	{
		List<GoodsAndAtt> reco = new ArrayList<>();
		String response;
		try {
			response = con_svc.post("/prod_recommend", map1);
		} catch (IOException | ParseException e) {
			System.err.println("Connection Error: Python");
			e.printStackTrace();
			return null;
		}
		if(response.equals("There is No Data")) return null;
				
		System.out.println("svc_response:"+response);
		
		String[] strarr = response.replace("[  ","").replace("]","").split(",  ");
		for (int i = 0; i<strarr.length;i++) {
			System.err.println("String_Arr: "+strarr[i]);
			int goodsnum = Integer.parseInt(strarr[i]);
			GoodsAndAtt goods_info = map.recommend(goodsnum);
			reco.add(goods_info);
		}		
		
		return  reco;
	}
	 
	 

}