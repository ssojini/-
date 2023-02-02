package com.example.demo.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data 
@ToString
@EqualsAndHashCode(exclude= {"adminpwd"})
@AllArgsConstructor 
@Entity
@NoArgsConstructor
public class Admin {
	@Id
	private String adminid;
	private String adminpwd;
	
	

}
