package com.example.demo.vo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
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
public class AdminBoard 
{
	public AdminBoard(int adnum) {
		this.adnum= adnum;
	}
	
	@Id
	@SequenceGenerator(name="ADMIN_BOARD_ADNUM_GEN",allocationSize=1,sequenceName="ADMIN_BOARD_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ADMIN_BOARD_ADNUM_GEN")
	
	private int adnum;
	private String name;
	@Column(name = "title", nullable = false)
	private String title;
	@Column(length = 5000) 
	private String content;
	@CreationTimestamp
	private Timestamp adate;
	@ColumnDefault("0")
	private int hit;
	private String author;

	@Transient
	private List<AdminAttachBoard> attList = new ArrayList<>();

}
