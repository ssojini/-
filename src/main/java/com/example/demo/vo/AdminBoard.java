package com.example.demo.vo;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@Table(name="freeboard")
public class AdminBoard {
	@Id
	@GeneratedValue
	//@SequenceGenerator(name="FREEBOARD_FBNUM_GEN",allocationSize=1,sequenceName="FREEBOARD_FBNUM_SEQ")
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FREEBOARD_FBNUM_GEN")
	private int qnum;
	private int anum;
	private String title;
	private String author;
	private java.sql.Timestamp qdate;
	private java.sql.Timestamp adate;
	private int hit;
	private String content;
}
