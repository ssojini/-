package com.example.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.Freeboard;
import com.example.demo.vo.Shop;

public interface BoardListRepository extends JpaRepository<Freeboard,String>{
	Page<Freeboard> findBybname(Pageable pageable,String bname);
}
