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
	private String address;
	private int itemid;
	private String price;
	private int itemnum;
	private String delivery;
	private int num;
	
}
