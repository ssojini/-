package com.example.demo.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
	
	public boolean add(MultipartFile[] mfiles,HttpServletRequest request,HCalendar cal, AttachCalendar attcal,Schedule sc)
	{
		log.info("svc, mfiles.length={}", mfiles.length);
		ServletContext context = request.getServletContext();
		String savePath = fileStorageLocation.toUri().getPath();
		
		List<AttachCalendar>list = new ArrayList<>();
		try {
			for(int i=0;i<mfiles.length;i++) {
				
				if(mfiles[0].getSize()==0) continue;
				  mfiles[i].transferTo(new File(savePath+"/"+mfiles[i].getOriginalFilename()));

				  String pname = mfiles[i].getOriginalFilename();
				  String extension = pname.substring(pname.lastIndexOf(".")); //파일 확장자
				  
				  String fname = UUID.randomUUID() + extension;
				  attcal.setPname(pname);
				  attcal.setFname(fname);
				  
				  list.add(attcal);	
				  
			}
			log.info("list:"+list);
			
			int rows = cm.saveCalendar(cal);
			int ro = cm.saveSchedule(sc);
			int r =0;
			if(!list.isEmpty()) {				
				r = cm.saveAttach(list);
			}
			
			return rows>0 || ro>0;
			
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
		
		List<Map<String,String>> listMap = cm.todo(cDay.get(java.util.Calendar.YEAR), cDay.get(java.util.Calendar.MONTH)+1);
		
		map.put("listMap", listMap);
		log.info("listMap"+listMap);
		
		return map;
	}
}
