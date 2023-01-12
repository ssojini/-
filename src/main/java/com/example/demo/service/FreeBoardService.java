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
import com.example.demo.vo.FreeBoard;

import jakarta.servlet.http.HttpSession;

@Service
public class FreeBoardService {
	@Autowired
	private FreeboardRepository repo;
	
	public FreeBoard getFreeBoardByFbnum(Integer fbnum) {
		Optional<FreeBoard> freeBoard = repo.findById(fbnum);
		return freeBoard.isPresent()?freeBoard.get():null;
	}
	
	public List<FreeBoard> getFreeBoardList() {
		return repo.findAll();
	}
	
	public List<FreeBoard> getFreeBoardList(String bname) {
		return repo.findByBname(bname);
	}
	
	public List<Map<String,Object>> getListFreeBoardToListMap(String bname) {
		System.out.println("bname:"+bname);
		List<FreeBoard> listFreeBoard = repo.findByBname(bname);
		System.out.println("listFreeBoard:"+listFreeBoard);
		List<Map<String,Object>> listMap = new ArrayList<>();
		for (int i = 0; i < listFreeBoard.size(); i++) {
			FreeBoard freeboard = listFreeBoard.get(i);
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

	public FreeBoard addFreeBoard(HttpSession session, FreeBoard freeBoard) {
		freeBoard = repo.save(freeBoard);
		System.out.println("freeBoard:"+freeBoard);
		return freeBoard;
	}
	
	public FreeBoard updateContents(Integer fbnum, String contents) {
		Optional<FreeBoard> freeBoard = repo.findById(fbnum);
		if (freeBoard.isPresent()) {
			freeBoard.get().setContents(contents);
			return repo.save(freeBoard.get());
		} else {
			return null;
		}
	}
	
	public boolean deleteFreeBoard(Integer fbnum) {
		repo.deleteById(fbnum);
		return true;
	}
}
