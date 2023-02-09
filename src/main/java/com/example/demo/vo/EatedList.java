package com.example.demo.vo;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Immutable
@Table(name = "EATED_LIST")
public class EatedList {
	@Id
	@Column(name = "C_NUM")
	private String cNum;

	@Column(name = "DATETIME")
	private String dateTime;

	@Column(name = "S_NUM")
	private String sNum;

	@Column(name = "S_PNUM")
	private String sPnum;

	@Column(name = "WHEN")
	private String when;

	@Column(name = "CONTENT")
	private String content;

	@Column(name = "A_NUM")
	private String aNum;

	@Column(name = "A_PNUM")
	private String aPnum;

	@Column(name = "PNAME")
	private String pName;
}