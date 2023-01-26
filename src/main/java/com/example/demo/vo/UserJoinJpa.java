package com.example.demo.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data 
@ToString
@EqualsAndHashCode(exclude= {"profile","pwd","email","email1","email2",
					"phone","birth","nickname","address"})
@AllArgsConstructor 
@NoArgsConstructor
@Entity
@Table(name="userjoin")
public class UserJoinJpa {
	@Id
	private String userid;
	private String address;
	private String pwd;
	private String email;
	private String phone;
	private String birth;
	private String nickname;
	private String profile;
	private int usernum;
	
	
}
