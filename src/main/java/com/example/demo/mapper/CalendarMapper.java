package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.AttachCalendar;
import com.example.demo.vo.Calendar;
import com.example.demo.vo.Schedule;

@Mapper
public interface CalendarMapper 
{

	int saveCalendar(Calendar cal);

	int saveAttach(List<AttachCalendar> list);
	
	int saveSchedule(Schedule schedule);

}
