package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminAttachBoard {

	private int adnum;
	private String attname;
	private long attsize;
	private int attid;
}
