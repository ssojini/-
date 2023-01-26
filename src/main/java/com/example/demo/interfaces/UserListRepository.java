package com.example.demo.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.UserJoin;
import com.example.demo.vo.UserJoinJpa;

public interface UserListRepository extends JpaRepository<UserJoinJpa,String> {
	Page<UserJoinJpa> findAll(Pageable pageable);
}
