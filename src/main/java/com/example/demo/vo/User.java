package com.example.demo.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user")
public class User 
{
	//아이디
	@Id
	private String userid;
	//비밀번호
	private String pwd;
	//닉네임
	private String nickname;
	//생년월일
	private java.sql.Date birth;
	//핸드폰번호
	private String phone;
	//이메일
	private String email;
	//약관동의
	private String agree;
}
