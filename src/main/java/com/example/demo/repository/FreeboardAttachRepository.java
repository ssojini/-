package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.FreeboardAttach;

import jakarta.transaction.Transactional;

public interface FreeboardAttachRepository extends JpaRepository<FreeboardAttach,Integer> {
	public List<FreeboardAttach> findAllByFbnum(Integer fbnum);
	@Transactional
	public List<FreeboardAttach> deleteByFbnum(Integer fbnum);
}
