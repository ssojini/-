package com.example.demo.vo;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"goodsname","price","description","goods_detail","category"})
@Entity
@Table(name="goods")
public class Goods 
{
	@Id
	@SequenceGenerator(sequenceName = "ADDGOODS_SEQ",initialValue = 100, allocationSize = 1, name="GOODS_GOODSNUM_GEN")
	private int goodsnum;
	private String goodsname;
	private Integer price;
	private String description;
	private String goods_detail;
	private String category;

	
}

	


