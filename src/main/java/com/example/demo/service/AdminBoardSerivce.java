package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.mapper.AdminBoardMapper;
import com.example.demo.vo.AdminBoard;
import com.example.demo.vo.AttachBoard;
import com.example.demo.vo.OneBoard;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Service

@Slf4j
public class AdminBoardSerivce 
{
	@Autowired
	private AdminBoardMapper mapper;
	
	public List<OneBoard> qList()
	{
		List<Map<String, Object>> mlist = mapper.QAlist();
		List<OneBoard> list = new ArrayList<>();
		for(int i=0; i<mlist.size(); i++)
		{
			Map<String, Object> map= mlist.get(i);
			
			BigDecimal big = (java.math.BigDecimal)map.get("QNUM");
			
			OneBoard oneb = new OneBoard(big.intValue());
			boolean found= false;
			if(list.contains(oneb)) {
				oneb= list.get(list.indexOf(oneb));
				found =true;
			}
			oneb.setTitle((String)map.get("TITLE"));
			oneb.setAuthor((String)map.get("AUTHOR"));
				
			
			oracle.sql.TIMESTAMP ots = (oracle.sql.TIMESTAMP) map.get("QDATE");
			java.sql.Timestamp jts = null;
			try {
				jts = ots.timestampValue();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			oneb.setQdate(jts);
			log.info("QDATE={}", jts);
			
			big = (java.math.BigDecimal)map.get("HIT");
			oneb.setHit((big.intValue()));
		
			Object objname = map.get("ATTNAME");
			if(objname==null) //첨부파일 null이고
			{
				if(!found) list.add(oneb); //해당 게시글 리스트에 없으면 리스트에 넣주기
				continue;	
			}
			else { //첨부파일 있으면
				AttachBoard attb = new AttachBoard();
				attb.setAttname((String)objname);
				big= (BigDecimal)map.get("ATTSIZE");
				attb.setAttsize(big.intValue());
	
				oneb.getAttList().add(attb);
				if(!found) list.add(oneb);
			}
			
		}
	//	log.info("서비스로 넘어오는 date:"+ list);
		return list;
	}
	
	@Transactional
	public boolean uploadQueB(HttpServletRequest request, OneBoard oneb, MultipartFile[] mfiles)
	{
		log.info("svc, mfiles.length={}", mfiles.length);
		ServletContext context =request.getServletContext();
		String savePath = context.getRealPath("/WEB-INF/files");
		
		 List<AttachBoard> alist = new ArrayList<>();
		 
		try {
			if(!mfiles[0].isEmpty())//첨부파일 있으면
			{
				for(int i=0; i<mfiles.length; i++) {
						mfiles[i].transferTo(new File(savePath+"/"+mfiles[i].getOriginalFilename()));//첨부파일저장
						
						AttachBoard attb = new AttachBoard();
						attb.setAttname(mfiles[i].getOriginalFilename());
						attb.setAttsize(mfiles[i].getSize());
						alist.add(attb);
						
						int arow =mapper.saveAttach(alist);
						int brow = mapper.addQueBoard(oneb);
						boolean uploaded = brow>0 && arow>0;
						return uploaded;
				}
			}else {//첨부파일 없으면
				int brow= mapper.addQueBoard(oneb);
				boolean uploaded = brow>0;
				return uploaded;
				
			}
			
		} catch (Exception e) {	
				e.printStackTrace();
		}
		
		return false;
	
	}
	
	public OneBoard detailByQnum(int num)
	{
		List<Map<String, Object>> mlist = mapper.detailByQnum(num);
		
		Map<String, Object> boardMap = mlist.get(0);
		
		OneBoard oneb = new OneBoard();
		java.math.BigDecimal big =(java.math.BigDecimal)boardMap.get("QNUM");
		oneb.setQnum(big.intValue());
		String title =(String)boardMap.get("TITLE");
		oneb.setTitle(title);
		String author = (String)boardMap.get("AUTHOR");
		oneb.setAuthor(author);
		String content = (String)boardMap.get("CONTENT");
		oneb.setContent(content);
		
		if(boardMap.get("ATTID")!=null)
		{
			for(int i=0; i<mlist.size(); i++)
			{
				mlist.get(i);
			
				AttachBoard att = new AttachBoard();
				
				String attname = (String)boardMap.get("ATTNAME");
				att.setAttname(attname);
				java.math.BigDecimal bigd=(java.math.BigDecimal)boardMap.get("ATTSIZE");
				att.setAttsize(bigd.intValue());
				java.math.BigDecimal bigid = (java.math.BigDecimal)boardMap.get("ATTID");
				att.setAttid(bigid.intValue());
				
				oneb.getAttList().add(att);
			}
		}
		log.info("svc, attlist에 있는 첨부파일"+ oneb.getAttList());
		
		mapper.increaseHit(num);
		//oneb =mapper.findQueBoard(num);
		return oneb;
	}
	
	@Transactional
	public boolean addAdmin(HttpServletRequest request, AdminBoard adminb, MultipartFile[] mfiles)
	{
		//log.info("admin svc, mfiles.length={}", mfiles.length);

		ServletContext context =request.getServletContext();
		String savePath = context.getRealPath("/WEB-INF/files");
		
		 List<AttachBoard> alist = new ArrayList<>();
		try {
			//log.info("mfiles length:"+ mfiles.length);
			if(!mfiles[0].isEmpty())//첨부파일 있으면
			{
				for(int i=0; i<mfiles.length; i++) {
						mfiles[i].transferTo(new File(savePath+"/"+mfiles[i].getOriginalFilename()));//첨부파일저장
						
						AttachBoard attb = new AttachBoard();
						attb.setAttname(mfiles[i].getOriginalFilename());
						attb.setAttsize(mfiles[i].getSize());
						alist.add(attb);
						
						int arow =mapper.saveAttach_admin(alist);
						int brow = mapper.addAdminBoard(adminb);
						boolean added = brow>0 && arow>0;
						return added;
				}
			}else {//첨부파일 없으면
				int brow= mapper.addAdminBoard(adminb);
				boolean added = brow>0;
				return added;
				
			}
			
		} catch (Exception e) {	
				e.printStackTrace();
		}
		
		return false;
	
	}
	
	public boolean updateQueB(OneBoard oneb, int qnum) {
	
		String newTitle= oneb.getTitle();
		String newContent= oneb.getContent();
		
		OneBoard updated= new OneBoard();
		updated.setTitle(newTitle);
		updated.setContent(newContent);
		
		int row = mapper.updateQueB(qnum);
		if(row>0) { 
			return true;
		}
		
		return false;
		
	}

	public boolean deleteAttach(int attid)
	{
		return mapper.deleteAttach(attid)==1;
	}
	
	public List<AttachBoard> getAttachList(int qnum)
	{
		List<AttachBoard> attList =mapper.getAttachList(qnum);
		return attList;
		
	}


}
