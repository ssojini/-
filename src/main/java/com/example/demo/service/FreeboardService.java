package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.interfaces.FreeboardRepository;
import com.example.demo.vo.Freeboard;

import jakarta.servlet.http.HttpSession;

@Service
public class FreeboardService {
	@Autowired
	private FreeboardRepository repo;
	
	public Freeboard getByFbnum(Integer fbnum) {
		Optional<Freeboard> freeBoard = repo.findById(fbnum);
		return freeBoard.isPresent()?freeBoard.get():null;
	}
	
	public List<Freeboard> getList() {
		return repo.findAll();
	}
	
	public List<Freeboard> getListByBname(String bname) {
		return repo.findByBname(bname);
	}
	
	public List<Map<String,Object>> getListMapByBname(String bname) {
		List<Freeboard> listFreeBoard = repo.findByBname(bname);
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

	public Map<String,String> save(Freeboard freeBoard) {
		Freeboard saveFreeBoard = repo.save(freeBoard);
		
		Map<String,String> map = new HashMap<>();
		map.put("author", saveFreeBoard.getAuthor());
		map.put("bname", saveFreeBoard.getBname());
		map.put("contents", saveFreeBoard.getContents());
		map.put("title", saveFreeBoard.getTitle());
		map.put("dateTime", saveFreeBoard.getDateTime().toLocaleString());
		map.put("fbnum", ""+saveFreeBoard.getFbnum());
		map.put("hit", ""+saveFreeBoard.getHit());
		return map;
	}
	
	public Freeboard updateContents(Integer fbnum, String contents) {
		Optional<Freeboard> freeBoard = repo.findById(fbnum);
		if (freeBoard.isPresent()) {
			freeBoard.get().setContents(contents);
			return repo.save(freeBoard.get());
		} else {
			return null;
		}
	}
	
	public boolean deleteByFbnum(Integer fbnum) {
		repo.deleteById(fbnum);
		return true;
	}
}
