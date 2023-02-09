package com.example.demo.vo;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

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
@Subselect("SELECT a.c_num, a.datetime, b.s_num, b.s_pnum, b.when, b.content, c.a_num, c.a_pnum, c.pname\r\n"
		+ "FROM hcalendar a \r\n"
		+ "LEFT OUTER JOIN calen_schedule b\r\n"
		+ "ON a.c_num = b.s_pnum\r\n"
		+ "LEFT OUTER JOIN attachcalendar c\r\n"
		+ "ON b.s_num = c.a_pnum\r\n"
		+ "ORDER BY a.c_num DESC")
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