package com.example.demo.vo;

import java.util.ArrayList;
import java.util.List;

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
import lombok.ToString;

@Data 
@ToString
@EqualsAndHashCode(exclude= {})
@AllArgsConstructor 
@NoArgsConstructor
@Entity
@Table(name="calen_schedule")
public class Schedule {
	@Id
	@SequenceGenerator(sequenceName = "calen_schedule_SEQ", allocationSize = 1, name="calen_schedule_GEN")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "calen_schedule_GEN")
	private int s_num;
	private int s_pnum;
	private String when;
	private String content;
	@Transient
	private List<AttachCalendar> attlist = new ArrayList<>();
}

