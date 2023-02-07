package com.example.demo.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachBoard 
{
	private int anum;
	private int qnum;
	private String attname;
	private long attsize;
	private int attid;
}