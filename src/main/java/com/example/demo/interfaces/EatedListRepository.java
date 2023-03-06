package com.example.demo.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.EatedList;

public interface EatedListRepository extends JpaRepository<EatedList,String> {
}
