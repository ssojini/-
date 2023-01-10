package com.example.demo.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.Goods;

public interface GoodsRepository extends JpaRepository<Goods, Integer>
{

}
