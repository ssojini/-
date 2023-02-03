package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.interfaces.FreeboardRepository;
import com.example.demo.vo.Freeboard;

import jakarta.servlet.http.HttpSession;

@Service
public class FreeboardService {
	@Autowired
	private FreeboardRepository repo;
	
	public Freeboard getByFbnum(Integer fbnum) {
		Optional<Freeboard> findFreeboard = repo.findById(fbnum);
		if (findFreeboard.isPresent()) {
			return findFreeboard.get();
		}
		return null;
	}

	public List<Freeboard> getList() {
		return repo.findAll();
	}

	public Page<Freeboard> getListByBnameAndTitle(String bname, String title, Pageable pageable) {
		if (title == null || title.equals("")) {
			if (bname == null || bname.equals("")) {
				return repo.findAllByOrderByDatetimeDesc(pageable);
			} else {
				return repo.findByBnameOrderByDatetimeDesc(bname, pageable);
			}
		} else {
			if (bname == null || bname.equals("")) {
				return repo.findByTitleOrderByDatetimeDesc(title, pageable);
			} else {
				return repo.findByBnameAndTitleOrderByDatetimeDesc(bname, title, pageable);
			}
		}
	}

	public List<Map<String,Object>> toListMap(List<Freeboard> listFreeBoard) {
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
		return listMap;
	}

	public Freeboard save(HttpSession session, Freeboard freeBoard) {
		Freeboard saveFreeBoard = repo.save(freeBoard);
		return saveFreeBoard;
	}

	public Freeboard update(Freeboard freeboard) {
		Freeboard findFreeboard = repo.getOne(freeboard.getFbnum());
		findFreeboard.setTitle(freeboard.getTitle());
		findFreeboard.setContents(freeboard.getContents());
		Freeboard updateFreeboard = repo.save(findFreeboard);
		return updateFreeboard;
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

	public Map<String,String> freeboardToMap(Freeboard freeboard) {
		Map<String, String> map = new HashMap<>();
		map.put("author", freeboard.getAuthor());
		map.put("bname", freeboard.getBname());
		map.put("contents", freeboard.getContents());
		map.put("title", freeboard.getTitle());
		map.put("datetime", ""+freeboard.getDatetime());
		map.put("fbnum", ""+freeboard.getFbnum());
		map.put("hit", ""+freeboard.getHit());
		return map;
	}
	
	public Page<Freeboard> getPageByOrderByHitDesc(Pageable page) {
		Page<Freeboard> pageFreeboard = repo.findAllByOrderByHitDesc(page);
		return pageFreeboard;
	}
	public List<Freeboard> getListByOrderByHitDesc() {
		List<Freeboard> listFreeboard = repo.findAllByOrderByHitDesc();
		return listFreeboard;
	}
}
