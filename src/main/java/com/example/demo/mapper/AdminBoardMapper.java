package com.example.demo.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.AdminBoard;
import com.example.demo.vo.AttachBoard;
import com.example.demo.vo.OneBoard;

@Mapper
public interface AdminBoardMapper 
{		
	public List<Map<String, Object>> QAlist();
	
	public int addQueBoard(OneBoard oneb);	
	
	public int addAnsBoard(OneBoard oneb);
	
	public int saveAttach(List<AttachBoard> alist);

	public int saveAttach_admin(List<AttachBoard> alist);

	public List<Map<String, Object>> detailByQnum(int qnum);
	public int increaseHit(int qnum);
	
	public OneBoard findQueBoard(int qnum);
	
	public int addAdminBoard(AdminBoard adminb);
	public int increaseHIt_admin (int adnum);
	
	public int updateQueB (OneBoard oneb);
	
	public int deleteQueB(int qnum);
	
	public int deleteAttach(int attid);
	
	public int deleteAllF(int qnum);
	
	public List<AttachBoard> getAttachList(int qnum);
	
	public List<AttachBoard> getAListById(int attid);
	
	public AttachBoard getAttach(int attid);
}
