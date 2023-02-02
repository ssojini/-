package com.example.demo.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.Shop;


public interface ShopListRepository extends JpaRepository<Shop,String>{
	Page<Shop> findBystatus(Pageable pageable,String status);

}
