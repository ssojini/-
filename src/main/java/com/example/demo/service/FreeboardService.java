package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public List<Freeboard> getFreeBoardList(String bname) {
		return repo.findByBname(bname);
	}
	
	public List<Map<String,Object>> getListFreeBoardToListMap(String bname) {
		System.out.println("bname:"+bname);
		List<Freeboard> listFreeBoard = repo.findByBname(bname);
		System.out.println("listFreeBoard:"+listFreeBoard);
		List<Map<String,Object>> listMap = new ArrayList<>();
		for (int i = 0; i < listFreeBoard.size(); i++) {
			Freeboard freeboard = listFreeBoard.get(i);
			Map<String,Object> map = new HashMap<>();
			map.put("fbnum", freeboard.getFbnum());
			map.put("bname", freeboard.getBname());
			map.put("title", freeboard.getTitle());
			map.put("author", freeboard.getAuthor());
			map.put("contents", freeboard.getContents());
			listMap.add(map);
		}
		System.out.println("listMap:"+listMap);
		return listMap;
	}
}
