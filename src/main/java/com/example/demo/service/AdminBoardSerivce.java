package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.mapper.AdminBoardMapper;
import com.example.demo.vo.AttachBoard;
import com.example.demo.vo.OneBoard;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
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
		System.out.println("mlist:"+mlist);
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
				
			oneb.setQdate((java.sql.Timestamp)map.get("QDATE"));
			
			log.info((java.sql.Timestamp)map.get("QDATE")+"");
			oneb.setHit((Integer)map.get("HIT"));
		
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
			
			log.info("서비스로 넘어오는 date:"+ list.get(0).getQdate());
		}
		return list;
	}
	
	public boolean uploadQueB(HttpServletRequest request, OneBoard oneb, @RequestParam("attach") MultipartFile[] mfiles)
	{
		ServletContext context =request.getServletContext();
		String savePath = context.getRealPath("/WEB-INF/files");

		AttachBoard attb = new AttachBoard();
		try {
			for(int i=0; mfiles!=null && i<mfiles.length; i++) {
				//if(mfiles[0].getSize()==0) continue;
					mfiles[i].transferTo(new File(savePath+"/"+mfiles[i].getOriginalFilename()));//첨부파일저장		
					attb.setAttname(mfiles[i].getOriginalFilename());
					attb.setAttsize(mfiles[i].getSize());
					
			}		
		
		/*
		 List<AttachBoard> alist = new ArrayList<>();
		 
		try {
			for(int i=0; mfiles!=null && i<mfiles.length; i++) {
				//if(mfiles[0].getSize()==0) continue;
					mfiles[i].transferTo(new File(savePath+"/"+mfiles[i].getOriginalFilename()));//첨부파일저장
					
					AttachBoard attb = new AttachBoard();
					attb.setAttname(mfiles[i].getOriginalFilename());
					attb.setAttsize(mfiles[i].getSize());
					alist.add(attb);
			}
		*/
			
			int brow = mapper.addQueBoard(oneb);
		//	int arow =mapper.saveAttach(alist);
			int arow = mapper.saveAttach(attb);
			
			boolean uploaded = brow>0 && arow>0;
			return uploaded;
			
		} catch (Exception e) {	
				e.printStackTrace();
		}
		
		return false;
	
	}
	
}
