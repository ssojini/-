package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AttachService {
	public void saveAttach(HttpServletRequest request, MultipartFile[] files) {
		String savePath = request.getServletContext().getRealPath("/static/images");
		System.out.println("savePath:"+savePath);
	}
}
