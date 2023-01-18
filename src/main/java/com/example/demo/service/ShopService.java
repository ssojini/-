package com.example.demo.service;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.interfaces.AddGoods_AttReopsitory;
import com.example.demo.interfaces.CartRepository;
import com.example.demo.interfaces.GoodsRepository;
import com.example.demo.mapper.ShopMapper;
import com.example.demo.vo.AddGoods_Att;
import com.example.demo.vo.Admin;
import com.example.demo.vo.Cart;
import com.example.demo.vo.Goods;
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

	
	
	/* 상욱 시작 */
	/* 상품 상세보기 시작*/
	// 상품찾기
	public Goods getGoods(int goodsnum) 
	{
		Optional<Goods> goods = goods_repo.findById(goodsnum);
		//System.out.println(goods.get());
		return goods.isPresent()?goods.get():null;
	}
	public ArrayList<AddGoods_Att> getAddGoodsAtt(int goodsnum) {
		
		ArrayList<AddGoods_Att> list = attGoods_repo.findByGoodsnum(goodsnum);		
		
		// 이미지 경로: /src/main/resources/static/images/addgoods 
		// static 다음부터 경로 ex)images/addgoods/
		
		System.err.println("list: "+list);
		return list;
	}

	//상품 상세보기 끝
	
	/*장바구니 시작*/
	//장바구니 담기
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
		//System.err.println(cart);
		
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
		System.err.println("del_cnt: "+ajaxMsg.length);
		try {
			for(int i=0; i<ajaxMsg.length;i++)
			{
				System.err.println(ajaxMsg[i]);
				cart_repo.deleteById(Integer.valueOf(ajaxMsg[i]));			
			}	
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	//장바구니 선택 구매
	public void buySel() {
		
	}
	
	//장바구니 전체 구매
	public void buyAll() {
		
	}
	/*장바구니 끝*/
	
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
	 
	 public String filesave(MultipartFile file)
	 {
		 JsonObject json = new JsonObject();
		 
		    String fileRoot =  "C:\\summernote_image\\";	
		    String originalFileName = file.getOriginalFilename();	//오리지날 파일명
		    String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); //파일 확장자

		    String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명
		    File targetFile = new File(fileRoot + savedFileName);	
		    System.out.println("targetFile:  "+ targetFile);
		    try {
		        // 파일 저장
		        InputStream fileStream = file.getInputStream();
		        FileUtils.copyInputStreamToFile(fileStream, targetFile);
		        
		        // 파일을 열기위하여 common/getImg.do 호출 / 파라미터로 savedFileName 보냄.
		        json.addProperty("url", "/summernoteImage/"+savedFileName);  
		        json.addProperty("responseCode", "success");
		   
		    } catch (IOException e) {
		        FileUtils.deleteQuietly(targetFile);	
		        json.addProperty("responseCode", "error");
		        e.printStackTrace();
		    }
		   String jsonvalue = json.toString();
		   //System.out.println(jsonvalue);
		   return jsonvalue;
		   
		   //return json;

	 }
	 
	 
	 public boolean save(MultipartFile file, String goods_detail, List<String> fileList,Goods goods, AddGoods_Att att)
	 {
		 //filesave(file);
		 List<AddGoods_Att>list = new ArrayList<>();
		 String mainpic_original = file.getOriginalFilename();
		 String extension1 = mainpic_original.substring(mainpic_original.lastIndexOf("."));	
		 String mainpic_server = UUID.randomUUID() + extension1;	
		 //String a[] = detail_server.split("/summernoteImage/");
		 String detail_original = "1";
		 
		 //Map<String, Object> result = new HashMap<String, Object>();
			//원본 파일경로
			for(int i=0;i<fileList.size();i++){
				
			    att.setDetail_server(fileList.get(i));
			    att.setMainpic_original(mainpic_original);
			    att.setMainpic_server(mainpic_server);
				att.setDetail_original(detail_original);
				 list.add(att);

			}
		 
		 System.out.println(list.toString());
		 
		 goods.setGoods_detail(goods_detail);
		 int add =  map.addgoods(goods);
		 int addatt = map.addgoods_att(list);
	
			boolean added = false;
			if(add>0 && addatt>0)
			{
				added =true;
			}
			return added;
	 }





	
	 

 
}