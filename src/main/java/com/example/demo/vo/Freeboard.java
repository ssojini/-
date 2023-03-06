package com.example.demo.vo;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
//@Table(name="freeboard")
public class Freeboard {
	public Freeboard(String bname, String title) {
		this.bname = bname;
		this.title = title;
	}
	@Id
	@SequenceGenerator(name="FREEBOARD_FBNUM_GEN",allocationSize=1,sequenceName="FREEBOARD_FBNUM_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FREEBOARD_FBNUM_GEN")
	private Integer fbnum;
	@Column(name = "bname", nullable = false)
	private String bname;
	@Column(name = "title", nullable = false)
	private String title;
	private String author;
	@Column(length = 50000000) // 50MB
	private String contents;
	@CreationTimestamp
	private Timestamp datetime;
	@ColumnDefault("0")
	private Integer hit;
	private String userid;
}
