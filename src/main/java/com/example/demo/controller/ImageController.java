package com.example.demo.controller;

import java.io.InputStream;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/images")
public class ImageController {
	@Autowired
	ResourceLoader resourceLoader;

	@GetMapping(value="/{filepath}", produces=MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getImage(@PathVariable("filepath") String filepath) {
		System.out.println("getImage");
		try {
			Resource resource = resourceLoader.getResource("WEB-INF/files/" + filepath.replace("-", "/"));
			System.out.println("resource:"+resource);
			InputStream is = resource.getInputStream();
			int len = (int)resource.getFile().length();
			byte[] buf = new byte[len];
			
			is.read(buf);
			is.close();
			return buf;
		} catch(Exception e) {
			System.err.println("이미지 로드 실패");
			//e.printStackTrace();
		}
		return null;
	}
	
	// 반영
}
