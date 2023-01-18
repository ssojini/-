package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.CalendarService;
import com.example.demo.vo.AttachCalendar;
import com.example.demo.vo.Schedule;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/calen")
@Slf4j
public class CalendarController 
{
	@Autowired
	private CalendarService cs;
	
	
	@GetMapping("/getCalendar")
	public String getCalendar(Model model,
	            @RequestParam(value="day", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day) {
	 
	 Calendar cDay = Calendar.getInstance(); // 오늘
	 
	 if(day == null) {
		 day = LocalDate.now();
	 } else {
		 cDay.set(day.getYear(), day.getMonthValue()-1, day.getDayOfMonth()); // 오늘날짜에서 day값과 동일한 값으로...
	 }
	 
	 model.addAttribute("year",cDay.get(Calendar.YEAR)); //년
	 model.addAttribute("month", cDay.get(Calendar.MONTH)+1); // 월
	 model.addAttribute("day", day); //일
	 model.addAttribute("lastDay", cDay.getActualMaximum(Calendar.DATE)); // 마지막 일
	 model.addAttribute("today",LocalDate.now());
	 
	 
	 Calendar firstDay = cDay;
	 firstDay.set(Calendar.DATE, 1); // cDay에서 일만 1일로 변경
	 
//	 System.out.println(firstDay.get(Calendar.YEAR)+","+(firstDay.get(Calendar.MONTH)+1)+","+firstDay.get(Calendar.DATE));
//	 System.out.println("firstDay.get(Calendar.DAY_OF_WEEK):"+firstDay.get(Calendar.DAY_OF_WEEK));// 1 일요일, 2월요일, ....7 토요일
	
	 model.addAttribute("firstDayOfWeek", firstDay.get(Calendar.DAY_OF_WEEK));
	 
	 	return "html/calendar/Calendar";
	}
	@GetMapping("/showCalen")
	public String showCalendarAdd(String day,Model model) 
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d"); 
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd"); 
		
		String beforeDate = day;
		String afterDate = "";
		
		try {
			Date date = dateFormat.parse(beforeDate); // 기존 string을 date 클래스로 변환
			afterDate = dateFormat2.format(date); // 변환한 값의 format 변경
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		//System.err.println(afterDate);
		
		model.addAttribute("day",afterDate);
		
		return "html/calendar/CalendarAdd";
	}
	@PostMapping("/add")
	@ResponseBody
	public Map<String,Object> morningAdd(@RequestParam("files")MultipartFile[] mfiles,
            com.example.demo.vo.Calendar cal, AttachCalendar attcal, Schedule sc, HttpServletRequest request, Model m) 
	{	
		Map<String,Object> map = new HashMap<>();
		map.put("add", cs.add(mfiles, request, cal, attcal, sc));
		log.info(""+map);
		return map;
	}
	
}

