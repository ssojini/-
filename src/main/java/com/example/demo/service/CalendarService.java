package com.example.demo.service;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.mapper.CalendarMapper;
import com.example.demo.vo.AttachCalendar;
import com.example.demo.vo.HCalendar;
import com.example.demo.vo.Schedule;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CalendarService 
{
	@Autowired
	private CalendarMapper cm;
	
	private final Path fileStorageLocation;

	@Autowired
	public CalendarService(Environment env) 
	{
		this.fileStorageLocation = Paths.get("./src/main/resources/static/images/calendar")
				.toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new RuntimeException(
					"Could not create the directory where the uploaded files will be stored.", ex);
		}
	}
	public boolean add(MultipartFile[] mfiles,HttpServletRequest request,HCalendar cal,Schedule sc)
	{
		log.info("svc, mfiles.length={}", mfiles.length);
		ServletContext context = request.getServletContext();
		String savePath = fileStorageLocation.toUri().getPath();
		
		List<AttachCalendar>list = new ArrayList<>();
		
		try {
			if(!mfiles[0].isEmpty()) {
				
				for(int i=0;i<mfiles.length;i++) 
				{
					mfiles[i].transferTo(new File(savePath+"/"+mfiles[i].getOriginalFilename()));
					
					AttachCalendar attcal = new AttachCalendar();
					
					String pname = mfiles[i].getOriginalFilename();
					String extension = pname.substring(pname.lastIndexOf(".")); //파일 확장자
					
					String fname = UUID.randomUUID() + extension;
					attcal.setPname(pname);
					attcal.setFname(fname);
					
					list.add(attcal);	
				}
			}
			log.info("list:"+list);
			
			int rows = cm.saveCalendar(cal);
			int ro = cm.saveSchedule(sc);
			int r =0;
			if(!list.isEmpty()) {				
				r = cm.saveAttach(list);
			}
			return rows>0 && ro>0;
			
			//return mfiles.length+"개파일,"+board.toString()+", 저장="+rows +","+r;
		} catch (Exception e) {
		e.printStackTrace();
		
		}
		return false;
	}
	
	public Map<String, Object> getCalendar(LocalDate day) 
	{
		Map<String, Object> map = new HashMap<>();
		
		java.util.Calendar cDay = java.util.Calendar.getInstance();
		
		 if(day == null) {
			 day = LocalDate.now();
		 } else {
			 cDay.set(day.getYear(), day.getMonthValue()-1, day.getDayOfMonth()); // 오늘날짜에서 day값과 동일한 값으로...
		 }

		java.util.Calendar firstDay = cDay;
		firstDay.set(java.util.Calendar.DATE, 1); // cDay에서 일만 1일로 변경
	
		map.put("year", cDay.get(java.util.Calendar.YEAR));
		map.put("month", cDay.get(java.util.Calendar.MONTH)+1);	
		map.put("day", day);	//
		map.put("lastDay", cDay.getActualMaximum(java.util.Calendar.DATE));		
		map.put("today",LocalDate.now());
		map.put("firstDayOfWeek", firstDay.get(java.util.Calendar.DAY_OF_WEEK));
		map.put("todayday", LocalDate.now().getDayOfMonth());
		map.put("dayday", cDay.get(java.util.Calendar.DATE));
		
		return map;
	}
	public List<Map<String,Object>> listCalendar()
	{
		List<Map<String,Object>> mlist = cm.list();
		
		List<Map<String,Object>> list = new ArrayList<>();
	
		for (int i = 0; i < mlist.size(); i++) 
		{
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> m = mlist.get(i);
			
			HCalendar cal = new HCalendar();
			
			BigDecimal big = (java.math.BigDecimal)m.get("NUM"); 
			cal.setNum(big.intValue());
			
			Timestamp time = (Timestamp) m.get("DATETIME");
			Date tdate = new Date(time.getTime());
			cal.setDatetime(tdate);
			
			map.put("tdate", tdate.getDate());
			map.put("tmonth", tdate.getMonth()+1);
			
			Schedule sch = new Schedule();
			
			sch.setWhen((String) m.get("WHEN"));
			BigDecimal sbig = (java.math.BigDecimal)m.get("NUM");
			sch.setNum(sbig.intValue());
			BigDecimal big1 = (java.math.BigDecimal)m.get("PNUM");
			sch.setPnum(big1.intValue());
			sch.setContent((String)m.get("CONTENT"));
			
			
			String sPname = (String) m.get("PNAME");
			String[] file = sPname.split(",");
			for (int j = 0; j < file.length; j++) {
				
				AttachCalendar att = new AttachCalendar();
				
				BigDecimal abig = (BigDecimal) m.get("NUM");
				BigDecimal acbig = (BigDecimal) m.get("PNUM");
				att.setPnum(acbig.intValue());
				att.setFname((String) m.get("FNAME"));
				att.setPname(file[j]);
				att.setNum(abig.intValue());
				
				sch.getAttlist().add(att);
			}
			
			map.put("cal", cal);
			map.put("sch", sch);
			
			list.add(map);
		}
			
		System.err.println(list);
		
		return list;
	}

	public List<Map<String,Object>> detailCalendar(int num)
	{
		
		List<Map<String,Object>> mlist = cm.detail(num);
		List<Map<String,Object>> list = new ArrayList<>();
		
		Map<String, Object> map = new HashMap<>();
		
		Map<String, Object> m = mlist.get(0);
		
		HCalendar cal = new HCalendar();
		
		BigDecimal big = (java.math.BigDecimal)m.get("NUM"); 
		cal.setNum(big.intValue());
		
		Timestamp time = (Timestamp) m.get("DATETIME");
		Date tdate = new Date(time.getTime());
		cal.setDatetime(tdate);
		
		Schedule sch = new Schedule();
		
		BigDecimal sbig = (java.math.BigDecimal)m.get("NUM");
		sch.setNum(sbig.intValue());
		BigDecimal big1 = (java.math.BigDecimal)m.get("PNUM");
		sch.setPnum(big1.intValue());
		sch.setWhen((String) m.get("WHEN"));
		sch.setContent((String)m.get("CONTENT"));
		
		for (int i = 0; i < mlist.size(); i++) 
		{
			Map<String, Object> amap = mlist.get(i);
			
			AttachCalendar att = new AttachCalendar();
				
			BigDecimal abig = (BigDecimal) amap.get("NUM");
			BigDecimal acbig = (BigDecimal) m.get("PNUM");
			att.setPnum(acbig.intValue());
			att.setFname((String)amap.get("FNAME"));
			att.setPname((String)amap.get("PNAME"));
			att.setNum(abig.intValue());
			sch.getAttlist().add(att);
		}
			map.put("cal", cal);
			map.put("sch", sch);
			
			list.add(map);
		System.err.println(list);
		return list;
	}
	
	public Schedule updateCon(Schedule sch)
	{
		Schedule upsch = cm.updateContenet();
		sch.setContent(sch.getContent());
		return null;
	}
	
	@Transactional
	public boolean deleteAll(int num)
	{
		int drow = cm.schattdelete(num);
//		int crow = cm.attcaldelete(num);
//		int brow = cm.schdelete(num);
		int arow = cm.caldelete(num);
		
		log.info("num:"+num);
		
		System.err.println("arow"+arow);
		System.err.println("drow"+drow);
//		System.err.println("brow"+brow);
//		System.err.println("crow"+crow);
		// 수정필요
		if(arow>0 && drow>0) return true;
		
		return false;
	}

}
