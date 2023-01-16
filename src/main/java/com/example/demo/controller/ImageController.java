package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.ImageService;

@Controller
@RequestMapping("/images")
public class ImageController {
	@Autowired
	ResourceLoader resourceLoader;
	
	@GetMapping("/{path}")
	@ResponseBody
	public byte[] getImage(@PathVariable("path") String path) {
		return null;
	}
}
