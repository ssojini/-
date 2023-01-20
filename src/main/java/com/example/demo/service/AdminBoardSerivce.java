package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
				
			/*
			oracle.sql.TIMESTAMP ots = (oracle.sql.TIMESTAMP) map.get("QDATE");
			java.sql.Timestamp jts = null;
			try {
				jts = ots.timestampValue();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			*/
			try {
				String jts = String.valueOf(map.get("QDATE"));
				//System.out.println("jts:"+jts);
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
				Date parseDate;
				parseDate = dateFormat.parse(jts);
				oneb.setQdate(new java.sql.Timestamp(parseDate.getTime()));
			//	log.info("QDATE={}", jts);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
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
		//log.info("svc, mfiles.length={}", mfiles.length);
		ServletContext context =request.getServletContext();
		String savePath = context.getRealPath("/WEB-INF/files");
		List<AttachBoard> alist = new ArrayList<>();
		int brow = mapper.addQueBoard(oneb);
		 
		try {
			boolean uploaded=false;

			if(!mfiles[0].isEmpty())//첨부파일 있으면
			{
				for(int i=0; i<mfiles.length; i++) {
						//log.info("mfiles length"+ mfiles.length);
						mfiles[i].transferTo(new File(savePath+"/"+mfiles[i].getOriginalFilename()));//첨부파일저장
						
						AttachBoard attb = new AttachBoard();
						attb.setAttname(mfiles[i].getOriginalFilename());
						attb.setAttsize(mfiles[i].getSize());
						alist.add(attb);
					//	log.info("svc, attb 목록"+ alist );
	//					return uploaded;
				}
				
				int arow =mapper.saveAttach(alist);
				uploaded = brow>0 && arow>0;
				return uploaded;
				
			}else {//첨부파일 없으면
				uploaded = brow>0;
				return uploaded;
				
			}
			
		} catch (Exception e) {	
				e.printStackTrace();
		}
		
		return false;
	
	}
	
	@Transactional
	public boolean uploadAnsB(HttpServletRequest request, OneBoard oneb, MultipartFile[] mfiles)
	{
		log.info("svc, mfiles.length={}", mfiles.length);
		ServletContext context =request.getServletContext();
		String savePath = context.getRealPath("/WEB-INF/files_ans");
		List<AttachBoard> alist = new ArrayList<>();
		int ansrow =mapper.addAnsBoard(oneb);
		log.info("svc, ansrow 정수값:" + ansrow);
		boolean uploaded=false;

		try {
			if(!mfiles[0].isEmpty())//첨부파일 있으면
			{
				for(int i=0; i<mfiles.length; i++) {
						mfiles[i].transferTo(new File(savePath+"/"+mfiles[i].getOriginalFilename()));//첨부파일저장
						
						AttachBoard attb = new AttachBoard();
						attb.setAttname(mfiles[i].getOriginalFilename());
						attb.setAttsize(mfiles[i].getSize());
						attb.setQnum(oneb.getQnum());
						alist.add(attb);
						log.info("svc, attb 목록"+ alist );
	//					return uploaded;
				}
				
				int attrow =mapper.saveAttach_admin(alist);
				uploaded = ansrow>0 && attrow>0;
				
			}else {//첨부파일 없으면
				uploaded = ansrow>0;			
			}			
		} catch (Exception e) {	
				e.printStackTrace();
		}
		
		log.info("svc, uploaded 값"+ uploaded);
		return uploaded;
	
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
		try {
			String jts = String.valueOf(boardMap.get("QDATE"));
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
			Date parseDate;
			parseDate = dateFormat.parse(jts);
			oneb.setQdate(new java.sql.Timestamp(parseDate.getTime()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		if(boardMap.get("ATTID")!=null)
		{
			for(int i=0; i<mlist.size(); i++)
			{
				boardMap =mlist.get(i); //새로 boardMap을 해줘야지 안그러면 위에서 한 mlist.get(0)으로만 돌아간다.
			
				AttachBoard att = new AttachBoard();
				
				String attname = (String)boardMap.get("ATTNAME");
				log.info("attname:"+attname);
				att.setAttname(attname);
				java.math.BigDecimal bigd=(java.math.BigDecimal)boardMap.get("ATTSIZE");
				att.setAttsize(bigd.intValue());
				java.math.BigDecimal bigid = (java.math.BigDecimal)boardMap.get("ATTID");
				att.setAttid(bigid.intValue());
				
				oneb.getAttList().add(att);
			}
		}
		//log.info("svc, attlist에 있는 첨부파일"+ oneb.getAttList());
		
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
	
	@Transactional
	public boolean updateQueB(OneBoard oneb, int qnum, 
			HttpServletRequest request, MultipartFile[] mfiles) 
	{

		int row = mapper.updateQueB(oneb);
		
		boolean edited = false; // 게시글 수정
		if(row>0) edited = true; 
		log.info("svc, edited 불리언 값:"+edited);		
		//첨부파일 추가
		List<AttachBoard> alist = new ArrayList<>();
		boolean added= false; //첨부파일 추가 
		boolean uploaded = false;
		if(!mfiles[0].isEmpty())//첨부파일 있으면
		{
			added = addFiles(request, mfiles);
			uploaded = (row>0 == edited);
			log.info("svc, uploaded 불리언 값:"+uploaded);		

		}else {//첨부파일 없으면
			edited= row>0;
			return edited;
			
		}
		
		return uploaded;

		
	}

	public boolean addFiles(HttpServletRequest request, MultipartFile[] mfiles)
	{
		ServletContext context =request.getServletContext();
		String savePath = context.getRealPath("/WEB-INF/files");
		List<AttachBoard> alist = new ArrayList<>();
		 
		try {
			boolean uploaded=false;

				for(int i=0; i<mfiles.length; i++) {
						mfiles[i].transferTo(new File(savePath+"/"+mfiles[i].getOriginalFilename()));//첨부파일저장
						
						AttachBoard attb = new AttachBoard();
						attb.setAttname(mfiles[i].getOriginalFilename());
						attb.setAttsize(mfiles[i].getSize());
						alist.add(attb);
					//	log.info("svc, attb 목록"+ alist );
					//	return uploaded;
				}
				
				int arow =mapper.saveAttach(alist);
				uploaded = arow>0;
				return uploaded;
				
			
		} catch (Exception e) {	
				e.printStackTrace();
		}
		
		return false;
	}
	
	//시험용
	public boolean deleteIndiv(int attid)
	{
		int result =mapper.deleteAttach(attid);

		log.info("svc, result값:"+ result);
		if(result>0) {
			return true;
		}
		return false;
	}
	
	public boolean deleteAll(int qnum)
	{
		int arow = mapper.deleteAllF(qnum);
		int brow = mapper.deleteQueB(qnum);
		
		if(arow>0 && brow>0) return true;
		return false;
	}

	public boolean delFromServer(List<AttachBoard> attachList)
	{
		log.info("컨트롤러 첨부파일 리스트"+attachList);
		
		for(int i=0; i<attachList.size();i++)
		{
			try{	
				Path file= Paths.get("src/main/webapp/WEB-INF/files");
				file =file.resolve(attachList.get(i).getAttname());
				Files.deleteIfExists(file);
				return true;
			}catch (Exception e){
				log.error("Delete file error: "+e.getMessage());
			}
		}
	
		return false;
	}
	
	public List<AttachBoard> getAttachList(int qnum)
	{
		log.info("svc,qnum값:" +qnum);
		List<AttachBoard> attList =mapper.getAttachList(qnum);
		log.info("svc, attList값"+ attList);
		return attList;
	}

	public AttachBoard getAttach(int attid)
	{
		AttachBoard attach =mapper.getAttach(attid);
		return attach;
	}

	public OneBoard getQueBoard(int qnum)
	{
		return mapper.findQueBoard(qnum);
	}
	
	public int deleteQueB(int qnum)
	{
		return mapper.deleteQueB(qnum);
	}
}
