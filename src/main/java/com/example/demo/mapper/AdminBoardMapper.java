package com.example.demo.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.AdminAttachBoard;
import com.example.demo.vo.AdminBoard;
import com.example.demo.vo.AttachBoard;
import com.example.demo.vo.OneBoard;

@Mapper
public interface AdminBoardMapper 
{		
	
	/*공지사항 및 FAQ 용*/
	public int saveAdminAttach(List<AdminAttachBoard> alist);

	public int moreAttach_admin(List<AdminAttachBoard> alist);

	public List<Map<String, Object>> noticeList();
	
	public List<Map<String, Object>> faqList();
	
	public List<Map<String, Object>> detail_adminb(int adnum);

	public int addAdminBoard(AdminBoard adminb);
	
	public int increaseHit_admin (int adnum);
	
	public int updateAdminB (AdminBoard adminb);
	
	public int delAdminAttach (int attid);

	public AdminAttachBoard getAdminAttach(int attid);
	
	public List<AdminAttachBoard> getAdminAttachList(int adnum);
	
	public int delAdminB(int adnum);

	public int deleteAllF_admin(int adnum);

	/* */
}
