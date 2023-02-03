package com.example.demo.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.User;

public interface UserListRepository extends JpaRepository<User,String> {
	Page<User> findAll(Pageable pageable);

}
