package com.example.demo.controller;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.mapper.CalendarMapper;
import com.example.demo.service.CalendarService;
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
	@Autowired
	private CalendarMapper cm;
	
	@GetMapping("/getCalendar")
	public String getCalendar(@RequestParam(value="day",required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate day, Model model,String datetime)
	{
		Map<String, Object> map = cs.getCalendar(day);
		
		model.addAttribute("year",map.get("year")); //년
		model.addAttribute("month",map.get("month")); // 월
		model.addAttribute("day", map.get("day")); //일
		model.addAttribute("lastDay", map.get("lastDay")); // 마지막 일
		model.addAttribute("today", map.get("today"));
		model.addAttribute("firstDayOfWeek", map.get("firstDayOfWeek"));
//		model.addAttribute("listMap", cs.listCalendar(datetime));
		model.addAttribute("listMap", map.get("listMap"));
		
		log.info("todayday"+map.get("todayday"));
		log.info("day"+ map.get("today"));
		log.info("dayday"+map.get("dayday"));
		
	 	return "html/calendar/calendar";
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
		
		return "html/calendar/calendarAdd";
	}
	@PostMapping("/add")
	@ResponseBody
	public Map<String,Object> morningAdd(@RequestParam("files")MultipartFile[] mfiles,
            com.example.demo.vo.HCalendar cal, Schedule sc, HttpServletRequest request, Model m) 
	{	
		Map<String,Object> map = new HashMap<>();
		map.put("add", cs.add(mfiles, request, cal, sc));
		log.info(""+map);
		return map;
	}
	@GetMapping("/detail/{num}")
	public String calenDetail(@PathVariable("num") int num, Model model)
	{
		model.addAttribute("mlist",cs.detailCalendar(num));
		return "html/calendar/CalendarDetail";
	}
	@GetMapping("/edit/{num}")
	public String edit(@PathVariable("num")int num, Model model)
	{
		model.addAttribute("mlist",cs.detailCalendar(num));
		return "html/calendar/calendarEdit";
	}
	@PostMapping("/updateCon")
	@ResponseBody
	public Map<String, Object> updateContent(Schedule sch)
	{
		Map<String, Object> map = new HashMap<>();
		
		return map;
	}
	
	@PostMapping("/delete")
	@ResponseBody
	public Map<String,Object> deleteById(int num)
	{
		Map<String,Object> map = new HashMap<>();
		map.put("deleted", cs.deleteAll(num));
		return map;
	}

}

