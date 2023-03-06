package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.FreeboardAttachService;
import com.example.demo.service.FreeboardReplyService;
import com.example.demo.service.FreeboardService;
import com.example.demo.service.ManagerService;
import com.example.demo.service.ShopService;
import com.example.demo.vo.Admin;
import com.example.demo.vo.Freeboard;
import com.example.demo.vo.Manager;
import com.example.demo.vo.Order;
import com.example.demo.vo.Shop;
import com.example.demo.vo.User;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/manager")
@Slf4j
public class ManagerController {
	
	@Autowired
	private ManagerService svc;
	
	@Autowired
	private ShopService ssvc;
	
	@Autowired
	private FreeboardService freeboardService;
	
	@Autowired
	private FreeboardAttachService attachService;
	
	@Autowired
	private FreeboardReplyService replyService;

	
	@GetMapping("/")
	public String managerlogin()
	{
		return "html/manager/managerLogin";
	}
	
	@GetMapping("/main")
	public String managermain(Model m)
	{
		return "html/manager/ManagerMain";
	}
	
	@RequestMapping("/calchart")
	@ResponseBody
	public List<Map<String, Object>> calchart(Model m)
	{
		List<Map<String, Object>> cal = svc.chartcal();
		m.addAttribute("cal",cal);
		System.out.println(cal.toString());
		
		return cal;
	}
	
	@RequestMapping("/userchart")
	@ResponseBody
	public List<Map<String, Object>> userchart(Model m)
	{
		List<Map<String, Object>> user = svc.chartuser();
		m.addAttribute("user",user);
		System.out.println(user.toString());
		
		return user;
	}
	

	@PostMapping("/login")
	@ResponseBody
	public Map<String,Object> loginProc(Admin admin)
	{
		return svc.login(admin.getAdminid(),admin.getAdminpwd());
	} 
	
	@GetMapping("/userlist")
	public String userList(Model m, @PageableDefault(size=10,sort="userid", page=0) Pageable pageable) throws Exception
	{
		Page<User> list = svc.getUserList(pageable);
		m.addAttribute("userList",list);
		m.addAttribute("url", "manager/userdetail");
		return "html/manager/userList";
	}
	
	@GetMapping("/userdetail/{userid}")
	public String userdetail(@PathVariable String userid, Model m)
	{
		m.addAttribute("userdetail",svc.userdetail(userid));
		m.addAttribute("url", "manager/userboard/detail");
		return "html/manager/userdetail";
	}

	@PostMapping("/DelAccount")
	@ResponseBody
	public Map<String, Object> DelAccount(String userid, Model m)
	{
		Map<String,Object> map = new HashMap<>();
		map.put("result",svc.DelAccount(userid));
		return map;
	}
	
	@GetMapping("/shop")
	public String shop(Model m)
	{
		m.addAttribute("shop1", svc.shop1());
		m.addAttribute("shop2", svc.shop2());
		m.addAttribute("shop3", svc.shop3());
		m.addAttribute("shop4", svc.shop4());
		m.addAttribute("url", "manager/shoplist");
		return "html/manager/shop";
	}
	
	@GetMapping("/shoplist")
	public String shoplist(@RequestParam String status, @PageableDefault(size=10,sort="ordernum", page=0) Pageable pageable, Model m)
	{
		Page<Order> list = svc.getshop(status, pageable);
		m.addAttribute("status", status);
		m.addAttribute("url", "manager/shop/detail");
		m.addAttribute("shop", list);
		return "html/manager/shoplist";
	}
	
	@GetMapping("/shop/detail")
	public String shopdetail(@RequestParam int ordernum, Model m)
	{
		m.addAttribute("shop",svc.shopdetail(ordernum));
		return "html/manager/shopdetail";
	}
	
	@PostMapping("/shop/edit")
	@ResponseBody
	public Map<String,Object> shopedit(int ordernum, String status) 
	{
		Map<String,Object> map = new HashMap<>();
		Shop shop = new Shop();
		shop.setOrdernum(ordernum);
		shop.setStatus(status);
		svc.update(shop);
		
		map.put("result", true);
		return map;
	}
	
	@GetMapping("/board")
	public String boardList(@RequestParam String bname, Model m, @PageableDefault(size=10,sort="fbnum", page=0) Pageable pageable) throws Exception
	{
		Page<Freeboard> list = svc.getboardlist(bname, pageable);
		m.addAttribute("blist",list);
		m.addAttribute("bname",bname);
		m.addAttribute("url","manager/board/detail");
		return "html/manager/boardList";
	}
	
	@GetMapping("/board/detail")
	public String boarddetail(@RequestParam int fbnum, Model m) 
	{
		m.addAttribute("freeBoard",svc.boarddetail(fbnum));
		return "html/manager/boardDetail";
	}
	

	@PostMapping("/board/delete")
	@ResponseBody
	public Map<String,Object> delete(HttpServletRequest request, Model m, Integer fbnum) {
		Map<String,Object> map = new HashMap<>();
		boolean delete = freeboardService.deleteByFbnum(request, fbnum);
		map.put("result", delete);
		return map;
	}
	
	@GetMapping("/shopitem")
	public String shopitem(Model m)
	{
		m.addAttribute("eurl", "manager/editgoods");
		m.addAttribute("durl", "manager/item/delete");
		m.addAttribute("shopitem",svc.shopitem());
		return "html/manager/shopitem";
	}
	
	@GetMapping("/item/edit/{goodsnum}")
	public String itemedit(@PathVariable int goodsnum, Model m)
	{
		m.addAttribute("item",svc.itemedit(goodsnum)); 
		return "html/manager/shopitemedit";
	}
	
	@GetMapping("/editgoods/{goodsnum}")
	public String editGoodspage(@PathVariable(value = "goodsnum") int goodsnum, Model m)
	{

		m.addAttribute("goods", ssvc.editGoodspage(goodsnum));
		return "html/shop/goodsedit";
	}
	
	@PostMapping("/deletegoods/{goodsnum}")
	@ResponseBody
	public Map<String,Object> deletegoods(@PathVariable int goodsnum)
	{
		Map<String,Object> map = new HashMap<>();
		map.put("deleted",svc.deletegoods(goodsnum));
		return map;
	}

}	
