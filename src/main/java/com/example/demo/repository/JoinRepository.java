package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.User;


public interface JoinRepository extends JpaRepository<User, String>
{

	public User findByNickname(String nickname);

	public User findByEmail(String email);


}
