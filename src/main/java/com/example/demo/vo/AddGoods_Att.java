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
import lombok.ToString;

@Data 
@ToString
@EqualsAndHashCode(exclude= {"mainpic_original","mainpic_server","detail_original","detail_server","goodspho_seq"})
@AllArgsConstructor 
@NoArgsConstructor
@Entity
@Table(name="addgoods_att")
public class AddGoods_Att {
	
	private String mainpic_original;
	private String mainpic_server;
	private String detail_original;
	private String detail_server;
	@Id
	@SequenceGenerator(sequenceName = "addgoods_att_seq", allocationSize = 1, name="goodspho_seq")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "goodspho_seq")
	private int goodspho_seq;
	private int goodsnum;
	
	
}
