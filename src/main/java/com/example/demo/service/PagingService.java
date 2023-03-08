package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.interfaces.OneboardRepository;
import com.example.demo.vo.Freeboard;
import com.example.demo.vo.OneBoard;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PagingService 
{
	@Autowired
	private OneboardRepository repo; 
	
	public Page<OneBoard> getList(Pageable pageable) 
	{
		log.info("svc repository:"+ repo.findAllByOrderByQdateDesc(pageable));
		return repo.findAllByOrderByQdateDesc(pageable);
	}
}
