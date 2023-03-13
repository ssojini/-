package com.example.demo.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.repository.AdminboardRepository;
import com.example.demo.repository.OneboardRepository;
import com.example.demo.vo.AdminBoard;
import com.example.demo.vo.Freeboard;
import com.example.demo.vo.OneBoard;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PagingService 
{
	@Autowired
	private OneboardRepository repo; 
	
	@Autowired
	private AdminboardRepository admin_repo;
	
	
	public Page<OneBoard> getList(Pageable pageable, String userid) 
	{
		return repo.getQnaByAuthor(pageable,userid);
	}
	
	public Page<OneBoard> getListByTitle(Pageable pageable, String userid, String title)
	{
		return repo.getQnaByAuthorAndTitle(pageable, userid, title);
	}
	
	public Page<OneBoard> getAllList(Pageable pageable)
	{
		return repo.findQAList(pageable);
	}
	
	public Page<OneBoard> getAllListByTitle(Pageable pageable, String title)
	{
		return repo.findQAListByTitle(pageable, title);
	}
	
	public Page<AdminBoard> getNoticeOrFAQ(Pageable pageable, String name)
	{
		return admin_repo.findByNameOrderByAdateDesc(pageable, name);
	}

	public Page<AdminBoard> getNoticeOrFAQByTitle(Pageable pageable, String name, String title) {
		return admin_repo.findByNameAndTitleContainingOrderByAdateDesc(pageable, name, title);
	}
	
	
}
