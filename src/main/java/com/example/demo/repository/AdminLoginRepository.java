package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.Admin;

public interface AdminLoginRepository extends JpaRepository<Admin, String> 
{

	Admin findByAdminidAndAdminpwd(String adminid, String adminpwd);

}
