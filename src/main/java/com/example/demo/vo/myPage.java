package com.example.demo.vo;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data //get,set , equals.... 생략가능
@ToString
@EqualsAndHashCode(exclude= {"pwd","email","phone",})
@AllArgsConstructor //모든생성자
@NoArgsConstructor //기본생성자
public class myPage {
	
	private String userid;
	private String pwd;
	private String email;
	private String phone;
	private String birth;
	private String nickname;
	
}
