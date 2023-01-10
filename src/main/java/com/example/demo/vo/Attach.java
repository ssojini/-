package com.example.demo.vo;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Attach {
	@Id
	@SequenceGenerator(name="ATTACH_FNUM_GEN",allocationSize=1,sequenceName="ATTACH_FNUM_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ATTACH_FNUM_GEN")
	private Integer anum;
	private String aname;
	private BigDecimal asize;
}
