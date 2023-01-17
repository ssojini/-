package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.example.demo.service.AttachService;
import com.example.demo.service.FileService;
import com.example.demo.vo.Attach;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/file")
public class FileController {
	@Autowired
	private AttachService attachService;
	
	@PostMapping("/upload")
	@ResponseBody
	public Map<String,Object> upload(HttpServletRequest request, Model m, MultipartFile[] files, Integer fbnum) {
		Map<String,Object> map = new HashMap<>();
		List<Attach> save = attachService.upload(request, files, fbnum);
		map.put("result", true);
		map.put("liAttach", attachService.liAttachToLiMap(save));
		return map;
	}
	@GetMapping("/download")
	public ResponseEntity<Resource> donwload(HttpServletRequest request, String filename) {
		return attachService.donwload(request, filename);
	}
	@PostMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(HttpServletRequest request, Attach...attachs) {
		Map<String, Object> map = new HashMap<>();
		boolean delete = attachService.delete(request, attachs);
		map.put("result", delete);
		return map;
	}
}
