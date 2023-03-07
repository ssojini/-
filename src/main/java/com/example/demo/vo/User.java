package com.example.demo.vo;

import java.security.Principal;
import java.sql.Date;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"pwd","nickname","birth","phone","email","profile","address"})
@Entity
@Table(name="userjoin")
public class User implements Principal
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
	private String email1;
	private String email2;
	//프로필사진
	private String profile;
	//주소
	private String address;
	//활성화
	@ColumnDefault("1")
	private Integer enabled;
	private String role;
	
	public User(String userid, String pwd, String nickname, Date birth, String phone, String email, String profile, String role) {
		super();
		this.userid = userid;
		this.pwd = pwd;
		this.nickname = nickname;
		this.birth = birth;
		this.phone = phone;
		this.email = email;
		this.profile = profile;
		this.enabled = 1;
		this.role = role;
	}

	@Override
	public String getName() {
		return this.getUserid();
	}
}
