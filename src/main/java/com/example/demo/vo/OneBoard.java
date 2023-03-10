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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude= {"anum", "title", "author", "qdate", "hit", "content", "attList"
})
@Entity
@Table(name="queboard")
public class OneBoard 
{
	public OneBoard(int qnum) {
		this.qnum= qnum;
	}

	@Id
	@SequenceGenerator(name="QUEBOARD_QNUM_GEN",allocationSize=1,sequenceName="QUEBOARD_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="QUEBOARD_QNUM_GEN")
	private int qnum;
	private int anum;
	@Column(name = "title", nullable = false)
	private String title;
	private String author;
	@CreationTimestamp
	private Timestamp qdate;
	// private java.sql.Timestamp adate;
	@ColumnDefault("0")
	private int hit;
	@Column(length = 5000) // 50MB
	private String content;
	
	@Transient
	private List<AttachBoard> attList = new ArrayList<>();
}