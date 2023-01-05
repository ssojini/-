package com.example.demo.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.OneBoard;

@Mapper
public interface AdminBoardMapper 
{		
	public List<Map<String, Object>> QAlist();
		
}
