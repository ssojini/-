package com.example.demo.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.Admin;

public interface AdminLoginRepository extends JpaRepository<Admin, String> 
{

	Admin findByAdminidAndAdminpwd(String adminid, String adminpwd);

}
