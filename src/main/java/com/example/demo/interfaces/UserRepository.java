package com.example.demo.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.User;



public interface UserRepository extends JpaRepository<User, String> 
{

	User findByUseridAndPwd(String userid, String pwd);

	User findByUseridAndEmail(String userid, String email);

	User findByPhoneAndEmail(String phone, String email);

	

	

}