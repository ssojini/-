package com.example.demo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data 
@ToString
@EqualsAndHashCode(exclude= {"mainpic_original","mainpic_server","detail_original","detail_server"})
@AllArgsConstructor 
@NoArgsConstructor
public class AddGoods_Att {
	
	private String mainpic_original;
	private String mainpic_server;
	private String detail_original;
	private String detail_server;
	private int goodspho_seq;
}
