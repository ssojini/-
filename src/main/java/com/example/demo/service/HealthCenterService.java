package com.example.demo.service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.mapper.MapMapper;
import com.example.demo.vo.MapInfo;

@Service
public class HealthCenterService {
	
	@Autowired
	private MapMapper map;

	public List<MapInfo> centerinfo()
	{
		return map.center();
	}
	
	public List<MapInfo> center_search_detail(String area)
	{
		return map.center_search_detail(area);
	}
	
	
}
