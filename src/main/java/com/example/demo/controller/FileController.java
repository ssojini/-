package com.example.demo.controller;

import java.util.HashMap;
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

import com.example.demo.service.FileService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/file")
public class FileController {
	@Autowired
	private FileService fs;
	
	@PostMapping("/upload")
	@ResponseBody
	public Map<String,Object> upload(HttpServletRequest request, Model m, MultipartFile file, String filename) {
		Map<String,Object> map = new HashMap<>();
		boolean upload = fs.upload(request, file, filename);
		map.put("result", upload);
		return map;
	}
	@GetMapping("/download")
	public ResponseEntity<Resource> donwload(HttpServletRequest request, String filename) {
		return fs.donwload(request, filename);
	}
	@PostMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(HttpServletRequest request, String filename) {
		Map<String, Object> map = new HashMap<>();
		boolean delete = fs.delete(request, filename);
		map.put("result", delete);
		return map;
	}
}
