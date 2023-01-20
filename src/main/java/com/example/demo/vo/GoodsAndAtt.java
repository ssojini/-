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
	
	private Goods goods;
	private AddGoods_Att att;

}
