package com.example.demo.vo;

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