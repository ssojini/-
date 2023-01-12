package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.CalendarMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CalendarService 
{
	@Autowired
	private CalendarMapper cm;
}
