package com.example.demo.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.Authorities;

public interface AuthoritiesRepository extends JpaRepository<Authorities, String> {
	
}
