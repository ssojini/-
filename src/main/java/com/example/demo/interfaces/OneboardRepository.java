package com.example.demo.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.OneBoard;

public interface OneboardRepository extends JpaRepository<OneBoard, Integer>{
	public Page<OneBoard> findAll(Pageable pageable);
	public Page<OneBoard> findAllByOrderByQdateDesc(Pageable pageable);
	
}
