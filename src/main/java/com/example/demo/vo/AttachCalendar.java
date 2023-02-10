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
@Table(name="attachcalendar")
public class AttachCalendar 
{
	@Id
	@SequenceGenerator(sequenceName = "calen_attach_SEQ", allocationSize = 1, name="calen_attach_GEN")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "calen_attach_GEN")
	//시퀀스
	private int a_num;
	//파일시퀀스
	private int a_pnum;
	//원본사진이름
	private String pname;
	//서버저장이름
	private String fname;
}
