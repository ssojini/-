package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data 
@ToString
@AllArgsConstructor 
@NoArgsConstructor
public class nutrients {
	private String userid;
	private long cal;
	private long carb;
	private long protein;
	private long fat;
	private long sugars;
	private long sodium;
	private int s_num;
	private int s_pnum;
	private long serving_size;
	private String when;
	private String food_name;
}
