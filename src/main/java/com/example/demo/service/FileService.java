package com.example.demo.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class FileService {
	@Autowired
	private ResourceLoader resourceLoader;
	
	public ResponseEntity<Resource> donwload(HttpServletRequest request, String filepath) {
		String path = request.getServletContext().getRealPath("/WEB-INF/files");
		Resource resource = resourceLoader.getResource(path + filepath);
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		String filename = resource.getFilename();
		filename = filename.substring(filename.indexOf("_") + 1, filename.length());
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"").body(resource);
	}
	
	public boolean upload(HttpServletRequest request, MultipartFile file, String filename) {
		String savePath = request.getServletContext().getRealPath("/WEB-INF/files");
		try {
			file.transferTo(new File(savePath + filename));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean delete(HttpServletRequest request, String filepath) {
		try {
			String path = request.getServletContext().getRealPath("WEB-INF/files");
			Resource resource = resourceLoader.getResource(path + filepath);
			resource.getFile().delete();
			return true;
		} catch(Exception e) {
			System.err.println("파일 삭제 실패");
			//e.printStackTrace();
		}
		return false;
	}
}
