package com.example.demo.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="main_title")
public class Main_Title 
{
	@Id
	private int num;
	private String content;
}
