package com.example.demo.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.interfaces.FreeboardReplyRepository;
import com.example.demo.vo.FreeboardReply;

@Service
public class FreeboardReplyService {
	@Autowired
	private FreeboardReplyRepository repo;
	
	public FreeboardReply save(FreeboardReply reply) {
		return repo.save(reply);
	}
	
	public List<FreeboardReply> findAllByPnum(Integer pnum) {
		return repo.findAllByPnumOrderByDatetimeDesc(pnum);
	}
	
	public List<Map<String,String>> listReplyToListMap(List<FreeboardReply> listReply) {
		List<Map<String,String>> listMap = new ArrayList<>();
		for (int i = 0; i < listReply.size(); i++) {
			Map<String,String> map = new HashMap<>();
			map.put("num", ""+listReply.get(i).getNum());
			map.put("pnum", ""+listReply.get(i).getPnum());
			map.put("author", listReply.get(i).getAuthor());
			map.put("contents", listReply.get(i).getContents());
			Timestamp datetime = listReply.get(i).getDatetime();
			Date date = new Date(datetime.getTime());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			map.put("datetime", sdf.format(date));
			
			listMap.add(map);
		}
		return listMap;
	}
}
