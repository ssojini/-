package com.example.demo.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Shop 
{
	@Id
	private String userid;
	
	private int ordernum;
	
	private int itemid;
	
	private String itemname;
	
	private String totalprice;
	
	private String price;
	
	private int quantity;
	
	private String pdate;
	
	private String mypoint;
	
	private String itempoint;
	
	private String itemimg;
	
	private String address;
	
	private String status;
	
	
}
