package com.example.demo.vo;

import org.hibernate.annotations.CreationTimestamp;
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
@Table(name="tb_order")
public class Order {
	
	@Id
	@SequenceGenerator(sequenceName = "ORDER_SEQ", initialValue = 100, allocationSize = 1, name = "order_ordernum_gen")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_ordernum_gen")
	private int ordernum;
	private String userid;
	private int goodsnum;
	private String goodsname;
	private int price;
	private int prod_cnt;
	private int sum; //주문합계금액
	@CreationTimestamp
	private java.sql.Timestamp pdate;
	private int itempoint;
	private String mainpic_server;
	private String address;
	private String status;	

}
