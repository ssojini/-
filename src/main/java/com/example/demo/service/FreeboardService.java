package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.interfaces.FreeboardRepository;
import com.example.demo.vo.Freeboard;

@Service
public class FreeboardService {
	@Autowired
	private FreeboardRepository repo;
	
	public List<Freeboard> getFreeboardList() {
		return repo.findAll();
	}
}
