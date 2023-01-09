package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data 
@ToString
@EqualsAndHashCode(exclude= {"title","mainpic","price","description","goods_detail","attach","category"})
@AllArgsConstructor 
@NoArgsConstructor
public class Goods {
	
	private int goodsnum;
	private String title;
	private String mainpic;
	private int price;
	private String description;
	private String goods_detail;
	private String attach;
	private String category;
	
}
