package com.example.demo.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data 
@ToString
@EqualsAndHashCode(exclude= {})
@AllArgsConstructor 
@NoArgsConstructor
@Entity
@Table(name="hcalendar")
public class HCalendar {
	@Id
	@SequenceGenerator(sequenceName = "calendar_SEQ", allocationSize = 1, name="calendar_GEN")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "calendar_GEN")
	//시퀀스
	private int c_num;
	private String userid;
	private java.sql.Date datetime;
}