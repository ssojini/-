package com.example.demo.vo;


import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"goodsname","mainpic","price","description","goods_detail","attach","category"})
@Entity
@Table(name="goods")
public class Goods 
{
	@Id
	@SequenceGenerator(sequenceName = "ADDGOODS_SEQ", allocationSize = 1, name="GOODS_GOODSNUM_GEN")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GOODS_GOODSNUM_GEN")
	private int goodsnum;
	private String goodsname;
	//private String mainpic;
	private Integer price;

	private String description;
	private String goods_detail;
	//private String attach;
	private String category;

	// 동일한 상품을 판매하는 seller 가 존재할 수 있으므로 상품 테이블에 제조사가 아닌 판매자를 넣는 것은 맞지 않아 주석처리함.
	// private String seller;
}

	


