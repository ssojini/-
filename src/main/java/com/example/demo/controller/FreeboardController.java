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

@RequestMapping("/freeboard")
@Controller
public class FreeboardController {
	@Autowired
	private FreeboardService freeboardService;
	@Autowired
	private AttachService attachService;
	
	@GetMapping({"","/"})
	public String freeboard(Model m, String bname) {
		List<Freeboard> listFreeBoard = freeboardService.getListByBname(bname);
		m.addAttribute("listFreeBoard", listFreeBoard);
		m.addAttribute("bname",bname);
		return "html/freeboard/freeBoard";
	}

	@PostMapping("/getListMap")
	@ResponseBody
	public List<Map<String, Object>> getListMap(Model m, String bname) {
		List<Map<String, Object>> listMap = freeboardService.getListMapByBname(bname);
		return listMap;
	}

	@GetMapping("/add")
	public String add(Model m, String bname) {
		System.out.println("FreeboardController/add(Model m, String bname)");
		m.addAttribute("bname", bname);
		return "html/freeboard/addFreeBoard";
	}
	@PostMapping("/add")
	@ResponseBody
	public Map<String,Object> add(Model m, Freeboard freeBoard) {
		System.out.println("FreeboardController/add(Model m, Freeboard freeBoard)");
		Map<String,Object> map = new HashMap<>();
		Freeboard addFreeboard = freeboardService.save(freeBoard);
		map.put("result", true);
		map.put("freeboard", freeboardService.freeboardToMap(addFreeboard));
		return map;
	}
	
	@PostMapping("/updateContents")
	@ResponseBody
	public Map<String, Object> updateContents(Integer fbnum, String contents) {
		System.out.println("fbnum:"+fbnum);
		System.out.println("contents:"+contents);
		Map<String, Object> map = new HashMap<>();
		Freeboard updateFreeBoard = freeboardService.updateContents(fbnum, contents);
		map.put("result", updateFreeBoard!=null?true:false);
		return map;
	}

	@GetMapping("/detail")
	public String detail(Model m,Integer fbnum) {
		Freeboard freeBoard = freeboardService.getByFbnum(fbnum);
		m.addAttribute("freeBoard",freeBoard);
		return "html/freeboard/detailFreeBoard";
	}
	
	@PostMapping("/delete")
	@ResponseBody
	public Map<String,Object> delete(HttpServletRequest request, Model m, Integer fbnum) {
		Map<String,Object> map = new HashMap<>();
		boolean delete = freeboardService.deleteByFbnum(fbnum);
		List<Attach> listAttach = attachService.deleteByFbnum(request, fbnum);
		map.put("result", delete);
		return map;
	}
	
	@GetMapping("/edit")
	public String edit(Model m, Integer fbnum) {
		m.addAttribute("freeBoard",freeboardService.getByFbnum(fbnum));
		m.addAttribute("listAttach", attachService.getList(fbnum));
		return "html/freeBoard/editFreeBoard";
	}
}
