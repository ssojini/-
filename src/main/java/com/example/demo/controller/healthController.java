package com.example.demo.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.FileStorageService;
import com.example.demo.service.FreeboardService;
import com.example.demo.vo.Freeboard;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.mypageService;
import com.example.demo.vo.UserJoin;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/health")
@Slf4j
public class healthController {
	/* 다루한 */
	@Autowired
	private FreeboardService fs;

	@GetMapping("/freeboard")
	public String main(Model m) {
		List<Freeboard> freeboardList = fs.getFreeboardList();
		m.addAttribute("freeboardList", freeboardList);
		return "html/freeboard";
	}
	/* 다루한 */

	/* 현주 */
	@Autowired
	private mypageService mp_svc;
	
	@Autowired
	ResourceLoader resourceLoader;
	

	@Autowired
	private FileStorageService fs_svc;


	@GetMapping("/")
	@ResponseBody
	public String userlist()
	{	
		return mp_svc.userlist().toString();

	}
	
	@GetMapping("/calorie")
	   public String cal()
	   {
	      return "health/calorie";
	   }
	
	   @PostMapping("/cal")
	   @ResponseBody
	   public Map<String,Object> calculate(int height, int gender, int active)
	   {
	      Map<String, Object> map = new HashMap<>();
	      float recommand = (float) ( (height-100)*0.9*((gender*5)+20) ); 
	      //System.err.println(recommand+" Kcal");
	      map.put("recommand",recommand);
	      
	      return map;
	   }

	@GetMapping("/useredit/{userid}")
	public String addboardform(@PathVariable(value = "userid", required = false) String userid, Model m) {
		m.addAttribute("user", mp_svc.userinfo(userid));
		return "html/mypage/EditUser";
	}

	@PostMapping("/userEdit")
	@ResponseBody
	public Map<String,Object> useredit(@RequestParam("file")MultipartFile mfiles, 
											HttpServletRequest request, UserJoin userjoin) 
	{
		Map<String,Object> map= new HashMap<>();
		System.out.println("SYSTEM:  "+mp_svc.storeFile(mfiles, userjoin));
		map.put("edited", mp_svc.storeFile(mfiles,userjoin));
		return map;

	}

	@GetMapping("/deleteuser/{userid}")
	public String deleteuser_form(@PathVariable(value = "userid", required = false) String userid, Model m) {
		m.addAttribute("user", mp_svc.userinfo(userid));
		return "html/mypage/DeleteUser";
	}
	
	@GetMapping("/deleteuser_check/{userid}")
	public String deleteuser_check(@PathVariable(value = "userid", required = false) String userid, Model m) {
		m.addAttribute("user", mp_svc.userinfo(userid));
		return "html/mypage/DeleteUser_Check";
	}

	
	@PostMapping("/deleteuser")
	@ResponseBody
	public Map<String, Object> deleteuser(@PathVariable(value = "userid", required = false) String userid,  UserJoin userjoin, Model m) {
		m.addAttribute("user", mp_svc.userinfo(userid));
		Map<String, Object>map = new HashMap<>();
		map.put("deleted", mp_svc.deleteuser(userjoin));
		return map;
	}
	
	@GetMapping("/user_addinfo/{userid}")
	public String useraddinfo(@PathVariable(value = "userid", required = false) String userid, Model m) {
		m.addAttribute("user", mp_svc.userinfo(userid));
		return "html/mypage/UserDetail";
	}
	
	@GetMapping("/findpwd/{userid}")
	public String findpwd(@PathVariable(value = "userid", required = false) String userid, Model m) {
		m.addAttribute("user", mp_svc.userinfo(userid));
		return "html/mypage/FindPwd";
	}
	
	@PostMapping("/findpwd/{userid}")
	public String changepwd(@PathVariable(value = "userid", required = false) String userid, Model m) {
		m.addAttribute("user", mp_svc.userinfo(userid));
		return "html/mypage/FindPwd";
	}
	
	@GetMapping("/test1")
	public String test1() {
		return "html/mypage/test.html";
	}
	
	/* 현주 */
	

	/* 종빈 */
	@GetMapping("/test")
	public String test(Model m) {
		return "html/test/test";
	}
	/* 종빈 */
}
