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
public class AttachCalender 
{
	//시퀀스
	private int calen_anum;
	
	//파일시퀀스
	private int pnum;
	
	//원본사진이름
	private String m_pname;
	private String l_pname;
	private String d_pname;
	private String s_pname;
	
	//서버저장이름
	private java.sql.Timestamp ms_pname;
	private java.sql.Timestamp ls_pname;
	private java.sql.Timestamp ds_pname;
	private java.sql.Timestamp ss_pname;
	
}
