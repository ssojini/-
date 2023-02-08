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

	List<Map<String, Object>> detail(int num);

	List<Map<String, Object>> list();

	int updateContenet();

	int caldelete(int num);

	int schdelete(int num);

	int attcaldelete(int num);

	int schattdelete(int num);



}
