package com.example.demo.vo;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="freeboard_attach")
public class Attach {
	@Id
	@SequenceGenerator(name="ATTACH_ANUM_GEN",allocationSize=1,sequenceName="ATTACH_ANUM_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ATTACH_ANUM_GEN")
	private Integer anum;
	private Integer fbnum;
	private String aname;
	private long asize;
}
