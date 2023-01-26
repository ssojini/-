package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.FreeboardAttachService;
import com.example.demo.vo.AttachBoard;
import com.example.demo.vo.FreeboardAttach;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/file")
public class FileController {
	@Autowired
	private FreeboardAttachService attachService;
	
	@PostMapping("/upload")
	@ResponseBody
	public Map<String,Object> upload(HttpServletRequest request, Model m, MultipartFile[] files, Integer fbnum) {
		Map<String,Object> map = new HashMap<>();
		List<FreeboardAttach> save = attachService.upload(request, files, fbnum);
		map.put("result", true);
		map.put("liAttach", attachService.listAttachToListMap(save));
		return map;
	}
	@GetMapping("/download")
	public ResponseEntity<Resource> donwload(HttpServletRequest request, String filename) {
		return attachService.donwload(request, filename);
	}
	@PostMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(HttpServletRequest request, String arrAttach) {
		System.out.println("arrAttach:"+arrAttach);
		Map<String, Object> map = new HashMap<>();
		boolean delete = attachService.delete(request, attachService.jsonArrToListAttach(arrAttach));
		map.put("result", delete);
		return map;
	}
	
	@PostMapping("/list")
	@ResponseBody
	public Map<String,Object> list(Integer fbnum) {
		Map<String, Object> map = new HashMap<>();
		map.put("result", true);
		List<FreeboardAttach> listAttach = attachService.getList(fbnum);
		System.out.println("listAttach:"+listAttach);
		map.put("listAttach", listAttach);
		return map;
	}
}
