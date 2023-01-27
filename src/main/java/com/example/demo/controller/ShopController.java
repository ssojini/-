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

import com.example.demo.interfaces.CartRepository;
import com.example.demo.interfaces.GoodsRepository;
import com.example.demo.service.ShopService;
import com.example.demo.vo.AddGoods_Att;
import com.example.demo.vo.Cart;
import com.example.demo.vo.Goods;
import com.example.demo.vo.GoodsAndAtt;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/shop")
@Slf4j
public class ShopController {
	@Autowired
	private ShopService svc;
	@Autowired
	private GoodsRepository repo;
	@Autowired // 장바구니
	private CartRepository cart_repo;

	// 초기 테스트용
	@GetMapping("/")
	@ResponseBody
	public String index() {
		return "Shop Index";
	}

	// 초기 테스트 데이터 생성 메소드
	@GetMapping("/add")
	@ResponseBody
	public String testAdd() {
		// 상욱
		// 이미지 경로 확인해야함
		Goods goods = new Goods(0, "건강보조식품", 5000, "건강에 좋음", "상품에 관한 상세설명", "카테고리1");
		Goods added = repo.save(goods);
		goods = new Goods(0, "영양제", 1000, "건강에 좋음", "상품에 관한 상세설명", "카테고리2");
		added = repo.save(goods);
		Cart cart = new Cart(0, 0, "테스트_영양제", "goods.png", 100, 1, 100, "테스트", "asdf");
		Cart c_added = cart_repo.save(cart);
		// 하드코딩 addgoods_att

		// 이후 부분에 추가하여 수정 요망

		return "ShopController 초기 데이터 생성";
	}

	/*--------------------- 상욱 시작 ----------------------*/

	@GetMapping("/detail/{goodsnum}")
	public String detail(@PathVariable("goodsnum") int goodsnum, Model m) {
		// System.err.println(goodsnum);
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
		// 로그인 연동 후 주석해제
//		String userid = (String) session.getAttribute("userid");
		// 연동 전 userid 하드코딩
		String userid = "asdf";
		ArrayList<Cart> cartlist = svc.getCart(userid);
		// System.err.println("CART: "+cartlist);
		// System.err.println(cartlist.get(0).getMainpic());
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
		// System.err.println("controller start");
		// String[] ajaxMsg = request.getParameterValues("valueArr");
		// System.err.println("ajaxMsg1: "+ajaxMsg);
		boolean delSel = svc.delSel(request);
		// System.out.println("delSel: "+delSel);
		return "redirect: cart";
	}

	// 상품 구매
	// 즉시 구매
	@GetMapping("/buynow")
	public String buyNow(Cart cart, Model m) {
		// 구매목록을 orderList에 담아 보낸다.
		m.addAttribute("orderlist", svc.buyNow(cart));
		return "html/shop/orderItems";
	}

	// 장바구니 구매 (선택/전체)
	@GetMapping("/buycart")
	public String buyCart(@RequestParam String items, Model m) {

		m.addAttribute("orderlist",svc.buyCart(items));

		return "html/shop/orderItems";
	}

	@PostMapping("/buy")
	@ResponseBody
	public String buy(@RequestParam String paramList) {
		System.err.println("here");

		JSONParser parser = new JSONParser();
		try {
			JSONArray jsArr = (JSONArray) parser.parse(paramList);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		String json= parameters.get("paramList").toString();
//		ObjectMapper mapper = new ObjectMapper();
//	    List<Map<String, Object>> paramList = mapper.readValue(json, new TypeReference<ArrayList<Map<String, Object>>>(){});

		// List<dto> paramList = mapper.readValue(json, new
		// TypeReference<ArrayList<dto>>(){});
		// System.err.println(itemArr);
		return "성공";
	}

	/*--------------------- 상욱 끝 ----------------------*/

	/* 현주 */
	
	@GetMapping(value="/imgtest")
	public String imgtest()
	{
		return "html/shop/imgtest";
	}
	
	@GetMapping("/ShopMainPage")
	public String shopmainpage(Model m)
	{
		m.addAttribute("goodslist", svc.maingoods());
		m.addAttribute("newproduct", svc.newproduct());
		m.addAttribute("random", svc.randomproduct());
		
		return "html/shop/ShopMain";
	}

	@GetMapping("/addgoods/{adminid}")
	public String addgoods(@PathVariable(value = "adminid", required = false) String adminid, Model m) {
		m.addAttribute("admin", svc.admininfo(adminid));
		return "html/shop/AddGoods";

	}

	@RequestMapping(value = "/summer_image.do", produces = "application/json; charset=utf8")
	@ResponseBody
	public String uploadSummernoteImageFile(@RequestParam("file") MultipartFile multipartFile,
			HttpServletRequest request) throws IOException {
		return svc.filesave(multipartFile);
	}

	@PostMapping("/addgoods")
	@ResponseBody
	public Map<String, Object> addgoods(@RequestParam("main_file") MultipartFile file,
			@RequestParam("goods_detail") String goods_detail, @RequestParam("file[]") List<String> fileList,
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