package com.example.demo.mapper;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.json.simple.JSONArray;

import com.example.demo.vo.AttachCalendar;
import com.example.demo.vo.HCalendar;
import com.example.demo.vo.Schedule;

@Mapper
public interface CalendarMapper 
{

	int saveCalendar(HCalendar cal);

	int saveAttach(List<AttachCalendar> list);
	
	int saveSchedule(Schedule schedule);

	JSONArray scheduleShow(Date datetime);

	List<Map<String,String>> todo(Integer year, Integer month);

}
