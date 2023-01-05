package com.example.demo.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.FreeBoard;

public interface FreeboardRepository extends JpaRepository<FreeBoard, Integer> {
	public List<FreeBoard> findByBname(String bname);
}
