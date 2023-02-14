package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.FreeboardAttachService;
import com.example.demo.service.FreeboardLikecountService;
import com.example.demo.service.FreeboardReplyService;
import com.example.demo.service.FreeboardService;
import com.example.demo.vo.FreeboardAttach;
import com.example.demo.vo.FreeboardReply;
import com.example.demo.vo.Freeboard;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/freeboard")
@Controller
public class FreeboardController {
	@Autowired
	private FreeboardService freeboardService;
	@Autowired
	private FreeboardAttachService attachService;
	@Autowired
	private FreeboardReplyService replyService;
	@Autowired
	private FreeboardLikecountService likecountService;
	@Autowired
	private HttpSession session;
	
	@GetMapping({"","/"})
	public String freeboard(Model m, String bname, String title, @PageableDefault(size=10, sort="fbnum"/*, direction = Sort.Direction.DESC */, page=0) Pageable pageable) {
		Page<Freeboard> pageFreeboard = freeboardService.getListByBnameAndTitle(bname,title,pageable);
		System.out.println("pageFreeboard:"+pageFreeboard.toList());
		m.addAttribute("bname",bname);
		m.addAttribute("title",title);
		m.addAttribute("pageFreeboard", pageFreeboard);
		return "html/freeboard/freeboard";
	}

	@GetMapping("/add")
	public String add(Model m, String bname) {
		m.addAttribute("bname",bname);
		return "html/freeboard/addFreeboard";
	}
	@PostMapping("/add")
	@ResponseBody
	public Map<String,Object> add(Model m, Freeboard freeBoard) {
		java.sql.Date date = new java.sql.Date(100);
		System.out.println("FreeboardController/add(Model m, Freeboard freeBoard)");
		Map<String,Object> map = new HashMap<>();
		String nickname = (String)session.getAttribute("nickname");
		System.out.println("nickname:"+nickname);
		freeBoard.setAuthor(nickname);
		Freeboard addFreeboard = freeboardService.save(session, freeBoard);
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
		if (freeBoard != null) {
			freeBoard.setHit(freeBoard.getHit()+1);
			freeboardService.save(session, freeBoard);
		}
		m.addAttribute("freeBoard",freeBoard);
		m.addAttribute("listReply",replyService.findAllByPnum(fbnum));
		m.addAttribute("likecount",likecountService.getCount(fbnum));
		String nickname = (String)session.getAttribute("nickname");
		m.addAttribute("likecountUser",likecountService.isLikecountUser(fbnum,nickname));
		return "html/freeboard/detailFreeboard";
	}
	
	@PostMapping("/delete")
	@ResponseBody
	public Map<String,Object> delete(HttpServletRequest request, Model m, Integer fbnum) {
		Map<String,Object> map = new HashMap<>();
		boolean delete = freeboardService.deleteByFbnum(request, fbnum);
		map.put("result", delete);
		return map;
	}
	
	@GetMapping("/edit")
	public String edit(Model m, Integer fbnum, String title, String contents) {
		System.out.println("title:"+title);
		System.out.println("contents:"+contents);
		Freeboard freeboard = freeboardService.getByFbnum(fbnum);
		if (title != null) {
			freeboard.setTitle(title);
			freeboard.setContents(contents);
		}
		m.addAttribute("freeboard",freeboard);
		m.addAttribute("listAttach", attachService.getList(fbnum));
		return "html/freeBoard/editFreeboard";
	}
	@PostMapping("/edit")
	@ResponseBody
	public Map<String,Object> edit(Freeboard freeboard) {
		System.out.println("freeboard:"+freeboard);
		Map<String,Object> map = new HashMap<>();
		freeboardService.update(freeboard);
		map.put("result", true);
		return map;
	}
	
	@PostMapping("/addReply")
	@ResponseBody
	public Map<String,Object> addReply(FreeboardReply reply) {
		System.out.println("addReply()");
		Map<String,Object> map = new HashMap<>();
		String userid = (String)session.getAttribute("nickname");
		reply.setAuthor(userid);
		FreeboardReply saveReply = replyService.save(reply);
		map.put("result",true);
		return map;
	}
	
	@PostMapping("/deleteReply")
	@ResponseBody
	public Map<String,Object> deleteReply(Integer num) {
		System.out.println("num:"+num);
		Map<String,Object> map = new HashMap<>();
		replyService.deleteByNum(num);
		map.put("result", true);
		return map;
	}
	
	@PostMapping("/getReply")
	@ResponseBody
	public Map<String,Object> getReply(Integer num) {
		Map<String,Object> map = new HashMap<>();
		List<Map<String,String>> listReply = replyService.findAllByPnumToListMap(num);
		map.put("listReply", listReply);
		return map;
	}
	
	@PostMapping("/listFreeboard")
	@ResponseBody
	public Map<String,Object> listFreeboard() {
		Map<String,Object> map = new HashMap<>();
		List<Freeboard> listFreeboard = freeboardService.getListByOrderByHitDesc();
		map.put("listFreeboard", listFreeboard);
		return map;
	}
	
	@PostMapping("/likeCount")
	@ResponseBody
	public Map<String,Object> likeCount(Integer fbnum) {
		System.out.println("likeCount fbnum:"+fbnum);
		Map<String,Object> map = new HashMap<>();
		String nickname = (String)session.getAttribute("nickname");
		boolean result = freeboardService.changeLikecount(fbnum, nickname);
		map.put("result", result);
		return map;
	}
}
