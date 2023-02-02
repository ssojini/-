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
@EqualsAndHashCode(exclude= {"adminpwd"})
@AllArgsConstructor 
@NoArgsConstructor
@Entity
@Table(name="admin")
public class Admin {
	@Id
	private String adminid;
	private String adminpwd;
}
