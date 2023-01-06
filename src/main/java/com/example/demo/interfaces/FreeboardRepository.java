package com.example.demo.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.Freeboard;

public interface FreeboardRepository extends JpaRepository<Freeboard, Integer> {
	public List<Freeboard> findByBname(String bname);
}
