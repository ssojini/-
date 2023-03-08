package com.example.demo.vo;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude= {"anum", "title", "author", "qdate", "adate", "hit", "content", "attList","key"
})
@Entity
@Table(name="queboard")
public class OneBoard 
{
	public OneBoard(int qnum) {
		this.qnum= qnum;
	}

	@Id
	private int qnum;
	private int anum;
	private String title;
	private String author;
	private String qdate;
	// private java.sql.Timestamp adate;
	private int hit;
	private String content;
	
	@Transient
	private List<AttachBoard> attList = new ArrayList<>();
	
	private String key;
}