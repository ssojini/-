package com.example.demo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
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

		Clob clb = (Clob)map.get("CONTENT");
		try {
			String strcontent = parseClobToString(clb);
			oneb.setContent(strcontent);
			
			oracle.sql.TIMESTAMP timestamp = (oracle.sql.TIMESTAMP) map.get("QDATE");
			long milliseconds = timestamp.timestampValue().getTime();
			java.sql.Timestamp javaTimestamp = new java.sql.Timestamp(milliseconds);
			oneb.setQdate(javaTimestamp);

		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return oneb;
	}
	
	public static String parseClobToString(Clob clob) throws SQLException, IOException {
        if (clob == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        try (Reader reader = clob.getCharacterStream();
             BufferedReader br = new BufferedReader(reader)) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        }
        return sb.toString();
    }

	public List<Map<String, Object>> getOnebList(String author)
	{
		List<Map<String, Object>> mlist =qamapper.qna(author);
		return mlist;
	}
}
