package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.AttachService;
import com.example.demo.service.FreeboardService;
import com.example.demo.vo.Attach;
import com.example.demo.vo.Freeboard;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/freeboard")
@Controller
public class FreeboardController {
	@Autowired
	private HttpSession session;
	@Autowired
	private FreeboardService freeboardSvc;
	@Autowired
	private AttachService attachSvc;
	
	@GetMapping({"","/"})
	public String freeboard(Model m, String bname) {
		List<Freeboard> listFreeBoard = freeboardSvc.getListByBname(bname);
		m.addAttribute("listFreeBoard", listFreeBoard);
		m.addAttribute("bname",bname);
		return "html/freeboard/freeBoard";
	}

	@PostMapping("/getListMap")
	@ResponseBody
	public List<Map<String, Object>> getListMap(Model m, String bname) {
		List<Map<String, Object>> listMap = freeboardSvc.getListMapByBname(bname);
		return listMap;
	}

	@GetMapping("/add")
	public String add(Model m, String bname) {
		m.addAttribute("bname", bname);
		return "html/freeboard/addFreeBoard";
	}
	@PostMapping("/add")
	@ResponseBody
	public Map<String,Object> add(Model m, Freeboard freeBoard) {
		Map<String,Object> map = new HashMap<>();
		Map<String,String> addFreeBoard = freeboardSvc.save(freeBoard);
		map.put("result", true);
		map.put("freeBoard", addFreeBoard);
		return map;
	}
	
	@PostMapping("/changeSrc")
	@ResponseBody
	public Map<String, Object> changeSrc(Integer fbnum, String contents) {
		System.out.println("fbnum:"+fbnum);
		System.out.println("contents:"+contents);
		Map<String, Object> map = new HashMap<>();
		Freeboard updateFreeBoard = freeboardSvc.updateContents(fbnum, contents);
		map.put("result", updateFreeBoard!=null?true:false);
		return map;
	}

	@GetMapping("/detail")
	public String detail(Model m,Integer fbnum) {
		Freeboard freeBoard = freeboardSvc.getByFbnum(fbnum);
		m.addAttribute("freeBoard",freeBoard);
		return "html/freeboard/detailFreeBoard";
	}
	
	@PostMapping("/delete")
	@ResponseBody
	public Map<String,Object> delete(HttpServletRequest request, Model m, Integer fbnum) {
		Map<String,Object> map = new HashMap<>();
		boolean delete = freeboardSvc.deleteByFbnum(fbnum);
		List<Attach> listAttach = attachSvc.deleteByFbnum(fbnum);
		map.put("result", delete);
		return map;
	}
	
	@GetMapping("/edit")
	public String edit(Model m, Integer fbnum) {
		m.addAttribute("freeBoard",freeboardSvc.getByFbnum(fbnum));
		m.addAttribute("listAttach", attachSvc.getList(fbnum));
		return "html/freeBoard/editFreeBoard";
	}
}
