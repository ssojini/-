package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.EatedListRepository;
import com.example.demo.vo.EatedList;

@Service
public class EatedListService {
	@Autowired
	private EatedListRepository repo;
	
	public List<EatedList> getList() {
		List<EatedList> list = repo.findAll();
		return list;
	}
}
