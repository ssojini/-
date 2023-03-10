package com.example.demo.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.AdminBoard;
import com.example.demo.vo.Freeboard;

public interface AdminboardRepository extends JpaRepository<AdminBoard, Integer>{
	public Page<AdminBoard> findAll(Pageable pageable);
	public Page<AdminBoard> findAllByOrderByAdateDesc(Pageable pageable);
	public Page<AdminBoard> findByNameOrderByAdateDesc(Pageable pageable , String name);

}
