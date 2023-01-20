package com.example.demo.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="order")
public class Order {
	
	@Id
	@SequenceGenerator(sequenceName = "ORDER_SEQ", allocationSize = 1, name = "order_ordernum_gen")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_ordernum_gen")
	private int ordernum;
	private String userid;
	private int goodsnum;
	private String goodsname;
	private int sum;
	private int price;
	private int prod_cnt;
	private java.sql.Date pdate;
	private int itempoint;
	private String mainpic_orignal;
	private String address;
	private String status;	

}
