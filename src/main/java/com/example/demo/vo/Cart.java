package com.example.demo.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="cart")
public class Cart {
	
	@Id
	@SequenceGenerator(sequenceName = "CART_SEQ", allocationSize = 1, name="CART_CARTNUM_GEN")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CART_CARTNUM_GEN")
	private int cartnum;
	//상품번호
	private int goodsnum;
	//상품명
	private String goodsname;
	//대표
	private String mainpic_server;
	//상품단가	
	private Integer price;
	//주문수량
	private int prod_cnt;
	//주문금액
	private int sum;
	//카테고리
	private String category;
	//사용자
	private String userid; 
	

}