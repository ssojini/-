package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.AttachCalendar;
import com.example.demo.vo.HCalendar;
import com.example.demo.vo.Schedule;

@Mapper
public interface CalendarMapper 
{

	int saveCalendar(HCalendar cal);

	int saveAttach(List<AttachCalendar> list);
	
	int saveSchedule(Schedule schedule);

	//List<Map<String,Object>> scheduleShow(String datetime);

	List<Map<String,String>> todo(Integer year, Integer month);

	List<Map<String, Object>> detail(int num);

	List<Map<String, Object>> list();

}
