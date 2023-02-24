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
public class MapInfo {
	
	private String center_name; //이름
	private String center_add;  //주소
	private String center_call;  //전화번호 
	private String lat;   //위도
	private String longi;  //경도
	private String area; //지역
	
}
