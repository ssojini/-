package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data 
@ToString
@AllArgsConstructor 
@NoArgsConstructor
public class GoodsAndAtt {
	private int goodsnum;
	private String goodsname;
	private Integer price;
	private String description;
	private String goods_detail;
	private String category;
	private String mainpic_original;
	private String mainpic_server;
	private String detail_original;
	private String detail_server;
	private int goodspho_seq;

}
