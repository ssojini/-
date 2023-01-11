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
	private String mainpic;
	private Integer price;

	private String description;
	private String goods_detail;
	private String attach;
	private String category;

	private String seller;
	
	private List<AddGoods_Att> attlist = new ArrayList<>();
	
}

	


