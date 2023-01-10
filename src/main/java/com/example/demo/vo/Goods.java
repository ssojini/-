package com.example.demo.vo;

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

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"title","mainpic","price","description","goods_detail","attach","category"})
@Entity
@Table(name="goods")
public class Goods 
{
	@Id
	@SequenceGenerator(sequenceName = "ADDGOODS_SEQ", allocationSize = 1, name="GOODS_GOODSNUM_GEN")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GOODS_GOODSNUM_GEN")
	private int goodsnum;
	private String title;
	private String mainpic;
	private Integer price;
	private String description;
	private String goods_detail;
	private String attach;
	private String category;
	
}