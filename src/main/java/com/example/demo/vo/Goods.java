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
@EqualsAndHashCode(exclude= {"goodsname","mainpic","price","description","goods_detail","attach","category"})
@AllArgsConstructor 
@NoArgsConstructor
public class Goods {
	
	private int goodsnum;
	private String goodsname;
	private String mainpic;
	private int price;
	private String description;
	private String goods_detail;
	private String attach;
	private String category;
	private String seller;
	
	private List<AddGoods_Att> attlist = new ArrayList<>();
	
}
