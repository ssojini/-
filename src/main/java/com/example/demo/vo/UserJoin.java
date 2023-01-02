package com.example.demo.vo;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data 
@ToString
@EqualsAndHashCode(exclude= {"pwd","email","phone","birth","nickname"})
@AllArgsConstructor 
@NoArgsConstructor
public class UserJoin {
	
	private String userid;
	private String pwd;
	private String email;
	private String phone;
	private String birth;
	private String nickname;
	
}
