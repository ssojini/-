package com.example.demo.controller;

import java.io.InputStream;

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
@RequestMapping("/summernoteImage")
public class SummernoteImageController {
	@Autowired
	ResourceLoader resourceLoader;
	
	@GetMapping(value="/{filepath}", produces=MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getSummernoteImage(@PathVariable("filepath") String filepath) {
		try {
			Resource resource = resourceLoader.getResource("WEB-INF/files/goodsimg/" + filepath);
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
}
