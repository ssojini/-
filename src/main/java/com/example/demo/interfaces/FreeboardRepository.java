package com.example.demo.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.Freeboard;

public interface FreeboardRepository extends JpaRepository<Freeboard, Integer> {
	public Page<Freeboard> findAll(Pageable pageable);
	public Page<Freeboard> findByBname(String bname, Pageable pageable);
	public Page<Freeboard> findByBnameAndTitle(String bname, String title, Pageable pageable);
	public List<Freeboard> findAllByOrderByHitDesc();
}
