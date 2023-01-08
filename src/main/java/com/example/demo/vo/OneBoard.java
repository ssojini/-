package com.example.demo.vo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OneBoard 
{

	public OneBoard(int qnum) {
		this.qnum= qnum;
	}
	private int qnum;
	private int anum;
	private String title;
	private String author;
	private java.sql.Timestamp qdate;
	private java.sql.Timestamp adate;
	private int hit;
	private String content;
	private List<AttachBoard> attList = new ArrayList<>();


}
