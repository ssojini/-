package com.example.demo.vo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="admin_board")
public class AdminBoard // admin_board
{
	public AdminBoard(int adnum) {
		this.adnum= adnum;
	}
	@Id
	private int adnum;
	private String name;
	private String title;
	private String content;
	private String date_admin;
	private int hit;
	private String author;
	
	@Transient
	private List<AdminAttachBoard> attList = new ArrayList<>();

}
