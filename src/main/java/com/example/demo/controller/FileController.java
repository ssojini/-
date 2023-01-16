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
import com.example.demo.vo.FreeBoard;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/file")
public class FileController {
	@Autowired
	private AttachService svc;
	
	@PostMapping("/uploadFiles")
	@ResponseBody
	public Map<String,Object> UploadFiles(HttpServletRequest request, Model m, MultipartFile[] files, Integer fbnum) {
		System.out.println("1:"+fbnum);
		Map<String,Object> map = new HashMap<>();
		List<Map<String,String>> listAttach = svc.saveAttach(request, files, fbnum);
		System.out.println("listAttach:"+listAttach);
		map.put("listAttach", listAttach);
		return map;
	}
	@GetMapping("/downloadFile")
	public ResponseEntity<Resource> donwloadFile(HttpServletRequest request, Integer fbnum, String aname) {
		return svc.donwloadAttach(request, fbnum, aname);
	}
	@PostMapping("/deleteFiles")
	@ResponseBody
	public Map<String, Object> deleteFiles(Integer[] anum) {
		Map<String, Object> map = new HashMap<>();
		return map;
	}
}
