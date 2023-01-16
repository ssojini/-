package com.example.demo.service;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
	@Autowired
	private ResourceLoader resourceLoader;

	public byte[] getImage(String path) {
		try {
			Resource resource = resourceLoader.getResource(Paths.get("").toAbsolutePath().toString());
			System.out.println("resource:"+resource);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
