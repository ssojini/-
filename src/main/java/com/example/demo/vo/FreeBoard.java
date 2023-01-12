package com.example.demo.vo;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

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
//@Table(name="freeboard")
public class FreeBoard {
	@Id
	@SequenceGenerator(name="FREEBOARD_FBNUM_GEN",allocationSize=1,sequenceName="FREEBOARD_FBNUM_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FREEBOARD_FBNUM_GEN")
	private Integer fbnum;
	private String bname;
	private String title;
	private String author;
	@Column(length = 50000000) // 50MB
	private String contents;
	@CreationTimestamp
	private LocalDateTime dateTime;
	private Integer hit;
}
