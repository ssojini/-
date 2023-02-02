package com.example.demo.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.QAMapper;
import com.example.demo.vo.OneBoard;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HealthService 
{
	@Autowired
	private QAMapper qamapper;
	
	public List<OneBoard> qna(List<Map<String, Object>> mlist)
	{
		List<OneBoard> list = new ArrayList<>();
		for(int i=0; i<mlist.size(); i++)
		{
			Map<String, Object> map= mlist.get(i);
			OneBoard oneb = parseOneb(map);
			
			BigDecimal big = (java.math.BigDecimal)map.get("HIT");
			oneb.setHit((big.intValue()));
			list.add(oneb);
		}
		return list;
	}
	
	public OneBoard parseOneb(Map<String, Object> map)
	{
		BigDecimal big = (java.math.BigDecimal)map.get("QNUM");
		
		OneBoard oneb = new OneBoard(big.intValue());
		oneb.setTitle((String)map.get("TITLE"));
		oneb.setAuthor((String)map.get("AUTHOR"));

		try {
			String jts = String.valueOf(map.get("QDATE"));
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date parseDate;
			log.info("날짜:" + dateFormat.format(map.get("QDATE"))); 
			String date =dateFormat.format(map.get("QDATE"));
			oneb.setQdate(date);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return oneb;
	}
	
	public PageInfo<Map<String, Object>> getPage(int pg, int cnt, String author)
	{
		PageHelper.startPage(pg, cnt);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(qamapper.qna(author));
		List<Map<String, Object>> mlist = pageInfo.getList();
		
		return pageInfo;
	}
	
}
