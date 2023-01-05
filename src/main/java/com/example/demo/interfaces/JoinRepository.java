package com.example.demo.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.User;


public interface JoinRepository extends JpaRepository<User, String>
{


}
