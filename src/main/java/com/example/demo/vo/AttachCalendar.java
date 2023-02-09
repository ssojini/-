package com.example.demo.vo;

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
public class AttachCalendar 
{
	//시퀀스
	private int a_num;
	//파일시퀀스
	private int a_pnum;
	//원본사진이름
	private String pname;
	//서버저장이름
	private String fname;
}
