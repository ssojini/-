package com.example.demo.vo;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@Table(name="freeboard")
public class FreeBoard {
	@Id
	@GeneratedValue
	//@SequenceGenerator(name="FREEBOARD_FBNUM_GEN",allocationSize=1,sequenceName="FREEBOARD_FBNUM_SEQ")
	//@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FREEBOARD_FBNUM_GEN")
	private Integer fbnum;
	private String bname;
	private String title;
	private String author;
	private String contents;
	@CreationTimestamp
	private LocalDateTime dateTime;
	private Integer hit;
}
