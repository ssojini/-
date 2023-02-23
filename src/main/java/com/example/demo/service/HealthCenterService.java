package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
