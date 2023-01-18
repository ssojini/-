package com.example.demo.vo;

import java.sql.Timestamp;

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
public class FreeboardReply {
	@Id
	@SequenceGenerator(name="FREEBOARD_REPLY_NUM_GEN",allocationSize=1,sequenceName="FREEBOARD_REPLY_NUM_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FREEBOARD_REPLY_NUM_GEN")
	private Integer num;
	private Integer pnum;
	private String author;
	@Column(length = 5000000) // 5MB
	private String contents;
	@CreationTimestamp
	private Timestamp datetime;
}
