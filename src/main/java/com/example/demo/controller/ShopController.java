package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.repository.CartRepository;
import com.example.demo.repository.GoodsRepository;
import com.example.demo.service.ShopService;
import com.example.demo.vo.AddGoods_Att;
import com.example.demo.vo.Cart;
import com.example.demo.vo.Goods;
import com.example.demo.vo.GoodsAndAtt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/shop")
@Slf4j
public class ShopController {
	@Autowired
	private HttpSession session;
	@Autowired
	private ShopService svc;
	@Autowired
	private GoodsRepository repo;
	@Autowired // 장바구니
	private CartRepository cart_repo;

	

	/*--------------------- 상욱 시작 ----------------------*/

	@GetMapping("/detail/{goodsnum}")
	public String detail(@PathVariable("goodsnum") int goodsnum, Model m) {
		// 상품정보
		Goods goods = svc.getGoods(goodsnum);
		m.addAttribute("goods", goods);

		// 상품사진
		ArrayList<AddGoods_Att> attList = svc.getAddGoodsAtt(goodsnum);
		m.addAttribute("attList", attList);

		// 상품 리뷰

//		m.addAttribute("review", review);

		return "html/shop/goodsDetail";
	}

	// 별점 리뷰페이지
	@GetMapping("/detail/review")
	public String review() {
		return "html/shop/review";
	}

	// 장바구니 담기
	@PostMapping("/cart")
	@ResponseBody
	public Map<String, Object> addCart(Cart cart) {
		// System.err.println(cart);
		Map<String, Object> map = svc.added(cart);
		return map;
	}

	// 장바구니 보기
	@GetMapping("/cart")
	public String cart(Model m) {
		// 연동 전 userid 하드코딩
		//String userid = "asdf";
		String userid = (String) session.getAttribute("userid");
		ArrayList<Cart> cartlist = svc.getCart(userid);
		m.addAttribute("cartlist", cartlist);
		return "html/shop/cart";
	}

	// 장바구니 수량변경
	@PostMapping("/cnt_change")
	@ResponseBody
	public Map<String, Object> cnt_change(Cart cart) {
		Map<String, Object> map = svc.cnt_change(cart.getCartnum(), cart.getProd_cnt());
		return map;
	}

	// 장바구니 전체삭제
	@PostMapping("/delAll")
	@ResponseBody
	public Map<String, Object> delAll() {
		Map<String, Object> map = svc.delAll();
		return map;
	}

	// 장바구니 선택삭제
	@PostMapping("/delSel")
	@ResponseBody
	public String delSel(HttpServletRequest request) {
		boolean delSel = svc.delSel(request);
		return "redirect: cart";
	}

	// 상품 구매
	// 즉시 구매
	@PostMapping("/buynow")
	public String buyNow(Cart cart, Model m) {
		m.addAttribute("orderlist", svc.buyNow(cart));		
		return "html/shop/orderItems";
	}

	// 장바구니 구매 (선택/전체)
	@PostMapping("/buycart")
	public String buyCart(@RequestParam String items, Model m) {

		m.addAttribute("orderlist",svc.buyCart(items));

		return "html/shop/orderItems";
	}

	
	// 결제
	@GetMapping("/payment")	
	public String payment(@RequestParam String items
			, @RequestParam("userid") String userid
			, @RequestParam("address") String address
			, Model m) 
	{
		
		boolean completeBuy = svc.payment(items,userid,address);
		m.addAttribute("completeBuy", completeBuy);
		return "html/shop/completeBuy";
	}
	

	/*--------------------- 상욱 끝 ----------------------*/

	@GetMapping("/rec_test")
	@ResponseBody
	public String rec_test() throws IOException, ParseException
	{
		Map<String,String> map = new HashMap<>();
		String user = (String)session.getAttribute("userid");
		System.err.println("user: "+user);
		map.put("userid", user);
		return svc.recommand(map).toString();
	}
	/* 현주 */
	
	@GetMapping(value="/imgtest")
	public String imgtest()
	{
		return "html/shop/imgtest";
	}
	
	@GetMapping("/ShopMainPage")
	public String shopmainpage(Model m) throws IOException, ParseException
	{
		// 추천시스템
		Map<String,String> map = new HashMap<>();
		String user = (String)session.getAttribute("userid");
		map.put("userid", user);
		if(user!=null) {
			System.err.println("콘트롤러: "+svc.recommand(map));
			m.addAttribute("recommend", svc.recommand(map));
		}
		
		
		m.addAttribute("random", svc.randomproduct());
		m.addAttribute("newproduct", svc.newproduct());
		m.addAttribute("goodslist", svc.maingoods());
		
		return "html/shop/ShopMain";
	}

	@GetMapping("/addgoods/{adminid}")
	public String addgoods(@PathVariable(value = "adminid", required = false) String adminid, Model m) {
		m.addAttribute("admin", svc.admininfo(adminid));
		return "html/shop/AddGoods";

	}
	
	@GetMapping("/summer/{adminid}")
	public String summer(@PathVariable(value = "adminid", required = false) String adminid, Model m) {
		m.addAttribute("admin", svc.admininfo(adminid));
		return "html/shop/summernote";

	}
	

	@PostMapping(value = "/summer_image.do", produces = "application/json; charset=utf8")
	@ResponseBody
	public String uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile,
			HttpServletRequest request) throws IOException {
		return svc.filesave(multipartFile);
	}

	@PostMapping("/addgoods")
	@ResponseBody
	public Map<String, Object> addgoods(@RequestParam("main_file") MultipartFile file,
			@RequestParam("goods_detail") String goods_detail, @RequestParam(value="fileList[]") List<String> fileList,
			HttpServletRequest request, Goods goods, AddGoods_Att att) {
		boolean a =svc.storeFile(file, goods_detail, fileList, goods, att);
		boolean added = false;
		if (a==true)
		{
			added=true;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("added", added);

		return map;
	}
	
	@PostMapping("/editgoods")
	@ResponseBody
	public  Map<String, Object> editGoods(@RequestParam("main_file") MultipartFile file,
			@RequestParam("goods_detail") String goods_detail, @RequestParam("file[]") List<String> fileList,
			HttpServletRequest request, Goods goods, AddGoods_Att att)
	{
		svc.editgoods();
		return null;
	}
	
	@GetMapping("/searchgoods")
	public String searchGoods(@RequestParam(value="searchbox") String searchbox, Model m)
	{
		m.addAttribute("goodslist", svc.search(searchbox));
		return "html/shop/searchgoods";
	}
	
	@GetMapping("/category1")
	public String category1(Model m)
	{
		m.addAttribute("goodslist", svc.category1());
		return "html/shop/category1";
	}
	@GetMapping("/category2")
	public String category2(Model m)
	{
		m.addAttribute("goodslist", svc.category2());
		return "html/shop/category2";
	}
	@GetMapping("/category3")
	public String category3(Model m)
	{
		m.addAttribute("goodslist", svc.category3());
		return "html/shop/category3";
	}
	

	/* 종빈 */
	@GetMapping("/main")
	public String main() {
		return "html/shop/ShopMain";
	}

	@GetMapping("/mypage/{userid}")
	public String ShopMyPage(@PathVariable String userid, Model m) {
		m.addAttribute("list", svc.mypagelist(userid));
		m.addAttribute("url", "shop/mypage/itemdetail");
		return "html/shop/mypage/mypage";

	}

	@GetMapping("/mypage/itemdetail/{ordernum}")
	public String ShopDetail(@PathVariable String ordernum, Model m) {
		// m.addAttribute("detail", svc.shopDetail(userid, itemid));
		// return "html/shop/mypage/detail";
		m.addAttribute("detail", svc.shopDetail(ordernum));
		return "html/shop/mypage/detail";
	}
}