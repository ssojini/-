package com.example.demo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.AttachBoard;
import com.example.demo.vo.OneBoard;

@Mapper
public interface QAMapper {

	
	public List<Map<String, Object>> qaList();
	
	public List<Map<String, Object>> qna(String author);
	
	public int addQueBoard(OneBoard oneb);	
		
	public int saveAttach(List<AttachBoard> alist);
		
	public int moreAttach(List<AttachBoard> alist);
	
	public List<Map<String, Object>> detailByQnum(int qnum);
	
	public int increaseHit(int qnum);
	
	public OneBoard findQueBoard(int qnum);
	
	public int updateQueB (OneBoard oneb);

	public int deleteQueB(int qnum);
	
	public int deleteAttach(int attid);
	
	public int deleteAllF(int qnum);
	
	public List<AttachBoard> getAttachList(int qnum);
		
	public AttachBoard getAttach(int attid);

	
	public OneBoard search(String key);

}
