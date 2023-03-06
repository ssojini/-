package com.example.demo.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.interfaces.AdminLoginRepository;
import com.example.demo.interfaces.ShopListRepository;
import com.example.demo.interfaces.UserListRepository;
import com.example.demo.mapper.ManagerMapper;
import com.example.demo.vo.Admin;
import com.example.demo.vo.Freeboard;
import com.example.demo.vo.GoodsAndAtt;
import com.example.demo.vo.Order;
import com.example.demo.vo.Shop;
import com.example.demo.vo.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ManagerService {
	
	@Autowired
	private ManagerMapper map;
	
	@Autowired
	private UserListRepository urepo;
	
	@Autowired
	private ShopListRepository srepo;
	
	@Autowired
	private BoardListRepository brepo;
	
	@Autowired
	private AdminLoginRepository arepo;
	
	public List<User> userList()
	{
		return map.userList();
	}	

	public Map<String, Object> login(String adminid, String adminpwd) {
		Map<String,Object> map = new HashMap<>();
		Admin admin = arepo.findByAdminidAndAdminpwd(adminid, adminpwd);
		if(admin!=null) {
			map.put("login",true);			
			map.put("msg","로그인 성공");			
	
		}else {
			map.put("login",false);			
			map.put("msg","로그인 실패");	
		}
		return map;
	}

	public Page<User> getUserList(Pageable pageable) throws Exception {
        Page<User> page = urepo.findAll(pageable);

        return page; 

    }
	
	public User userdetail(String userid)
	{
		return map.userDetail(userid);
	}

	public Freeboard boarddetail(int fbnum) {
		return map.boardDetail(fbnum);
	}

	public boolean DelAccount(String userid) {
		int a = map.useridDel(userid);
		
		boolean result = false;
		if(a>0) result=true;
		return result;
	}

	public List<Order> shop1() {
		String status = "상품준비중";
		return map.shop1(status);
	}
	public List<Order> shop2() {
		String status = "배송준비중";
		return map.shop2(status);
	}
	public List<Order> shop3() {
		String status = "배송중";
		return map.shop3(status);
	}
	public List<Order> shop4() {
		String status = "배송완료";
		return map.shop4(status);
	}

	public Page<Order> getshop(String status, Pageable pageable) {
		Page<Order> page = srepo.findBystatus(pageable, status);
		return page;
	}

	public Order shopdetail(int ordernum) {
		return map.shopdetail(ordernum);
	}

	public int update(Shop shop) {
		Order findshop = map.shopdetail(shop.getOrdernum());
		findshop.setStatus(shop.getStatus());	
		int updateshop = map.shopUpdate(findshop);
		return updateshop;
	}

	public List<GoodsAndAtt> shopitem() {
		return map.shopitem();
	}

	public GoodsAndAtt itemedit(int goodsnum) {
		return map.getitem(goodsnum);
	}

	public boolean deletegoods(int goodsnum)
	{
		boolean deleted= false;
		int deletedgoods = map.deletegoods(goodsnum);
		int deletedatt = map.deletegoodsatt(goodsnum);
		if (deletedgoods>0 && deletedatt>0)
		{
			deleted=true;
		}
		return deleted;
	}

	public List<Freeboard> getboardlist(String bname) {
		return map.getboardlist(bname);
	}

	public Page<Freeboard> getboardlist(String bname, Pageable pageable) {
		Page<Freeboard> page = brepo.findBybname(pageable, bname);
		return page;
	}

	public List<Map<String, Object>> chartcal() {
		List<Map<String, Object>> list = map.chartcal();
		return list;
	}

	public List<Map<String, Object>> chartuser() {
		List<Map<String, Object>> list = map.chartuser();
		return list;
	}


}
