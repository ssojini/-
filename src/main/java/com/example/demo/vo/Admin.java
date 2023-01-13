package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data 
@ToString
@EqualsAndHashCode(exclude= {"adminpwd"})
@AllArgsConstructor 
@NoArgsConstructor
public class Admin {
	private String adminid;
	private String adminpwd;
	
	

}
