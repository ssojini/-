package com.example.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.Freeboard;

public interface FreeboardRepository extends JpaRepository<Freeboard, Integer> {
	public Page<Freeboard> findAll(Pageable pageable);
	public Page<Freeboard> findAllByOrderByDatetimeDesc(Pageable pageable);
	public Page<Freeboard> findByBnameOrderByDatetimeDesc(String bname, Pageable pageable);
	public Page<Freeboard> findByTitleOrderByDatetimeDesc(String title, Pageable page);
	public Page<Freeboard> findByBnameAndTitleOrderByDatetimeDesc(String bname, String title, Pageable pageable);
	public Page<Freeboard> findAllByOrderByHitDesc(Pageable page);
	public List<Freeboard> findAllByOrderByHitDesc();
}
