package com.example.demo.vo;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data 
@ToString
@EqualsAndHashCode(exclude= {})
@AllArgsConstructor 
@NoArgsConstructor
public class Schedule {

	private int s_num;
	private int s_pnum;
	private String when;
	private String content;
	
	private List<AttachCalendar> attlist = new ArrayList<>();
}

