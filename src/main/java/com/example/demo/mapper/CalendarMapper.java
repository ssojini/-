package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.AttachCalendar;
import com.example.demo.vo.HCalendar;
import com.example.demo.vo.Schedule;
import com.example.demo.vo.User;
import com.example.demo.vo.nutrients;

@Mapper
public interface CalendarMapper 
{

	int saveCalendar(HCalendar cal);

	int saveAttach(List<AttachCalendar> list);
	
	int saveSchedule(Schedule schedule);

	List<Map<String, Object>> detail(int num);
	
	nutrients food_info(int num);

	List<Map<String, Object>> list(String userid);

	int updateContenet(Schedule sch);

	int caldelete(int num);

	int schdelete(int num);

	int attcaldelete(int num);

	int schattdelete(int num);

	int delImg(int num);

	int saveAttach(int num);

	int updateAttach(List<AttachCalendar> list);

	User userinfo(String userid);
	
	int addnut(String food_name,String userid,String when);

}
