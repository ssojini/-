package com.example.demo.service;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.mapper.AdminBoardMapper;
import com.example.demo.mapper.QAMapper;
import com.example.demo.vo.AdminAttachBoard;
import com.example.demo.vo.AdminBoard;
import com.example.demo.vo.AttachBoard;
import com.example.demo.vo.OneBoard;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AdminBoardSerivce 
{
	@Autowired
	private AdminBoardMapper mapper;
	
	@Autowired
	private QAMapper qamapper;
	
	/* Q&A 관련 메소드 */
	public PageInfo<Map<String, Object>> getPage(int pg, int cnt)
	{
		PageHelper.startPage(pg, cnt);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(qamapper.qaList());
		List<Map<String, Object>> mlist = pageInfo.getList();
		
		return pageInfo;
	}
	
	public List<OneBoard> qaList(List<Map<String, Object>> mlist)
	{
		List<OneBoard> list = new ArrayList<>();
		for(int i=0; i<mlist.size(); i++)
		{
			Map<String, Object> map= mlist.get(i);
			OneBoard oneb = parseOneb(map);
			
			BigDecimal big = (java.math.BigDecimal)map.get("HIT");
			oneb.setHit((big.intValue()));
			list.add(oneb);
		}
		return list;
	}
	
	public OneBoard parseOneb(Map<String, Object> map)
	{
		BigDecimal big = (java.math.BigDecimal)map.get("QNUM");
		
		OneBoard oneb = new OneBoard(big.intValue());
		oneb.setTitle((String)map.get("TITLE"));
		oneb.setAuthor((String)map.get("AUTHOR"));
		oneb.setContent((String)map.get("CONTENT"));

		try {
			String jts = String.valueOf(map.get("QDATE"));
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date parseDate;
			log.info("날짜:" + dateFormat.format(map.get("QDATE"))); 
			String date =dateFormat.format(map.get("QDATE"));
			oneb.setQdate(date);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
		return oneb;
	}
	
	@Transactional
	public boolean uploadQueB(HttpServletRequest request, OneBoard oneb, MultipartFile[] mfiles)
	{

		//log.info("svc, mfiles.length={}", mfiles.length);
		ServletContext context =request.getServletContext();
		String savePath = context.getRealPath("/WEB-INF/files");
		List<AttachBoard> alist = new ArrayList<>();
		if(oneb.getQnum()!=0) 
		{	
			
			oneb.setAnum(oneb.getQnum());
		}
		int brow = qamapper.addQueBoard(oneb);
		 
		try {
			boolean uploaded=false;

			if(!mfiles[0].isEmpty())//첨부파일 있으면
			{
				for(int i=0; i<mfiles.length; i++) {
						mfiles[i].transferTo(new File(savePath+"/"+mfiles[i].getOriginalFilename()));//첨부파일저장
						
						AttachBoard attb = new AttachBoard();
						attb.setAttname(mfiles[i].getOriginalFilename());
						attb.setAttsize(mfiles[i].getSize());
						alist.add(attb);
				}		
				int arow =qamapper.saveAttach(alist);
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
	
	public OneBoard detailByQnum(int num)
	{
		List<Map<String, Object>> mlist = qamapper.detailByQnum(num);	
		Map<String, Object> boardMap = mlist.get(0);
		OneBoard oneb = parseOneb(boardMap);
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
		qamapper.increaseHit(num);
		return oneb;
	}
	
	@Transactional
	public boolean updateQueB(HttpServletRequest request, OneBoard oneb, MultipartFile[] mfiles)
	{
		//log.info("svc, mfiles.length={}", mfiles.length);
		ServletContext context =request.getServletContext();
		String savePath = context.getRealPath("/WEB-INF/files");
		List<AttachBoard> alist = new ArrayList<>();
		int brow = qamapper.updateQueB(oneb);
		 
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
						attb.setQnum(oneb.getQnum());
						log.info("qnum값"+oneb.getQnum());
						alist.add(attb);
					//	log.info("svc, attb 목록"+ alist );
	//					return uploaded;
				}
				
				int arow =qamapper.moreAttach(alist);
				log.info("svc, arow 값"+arow);
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
				
				int arow =qamapper.saveAttach(alist);
				uploaded = arow>0;
				return uploaded;
				
			
		} catch (Exception e) {	
				e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean deleteIndiv(int attid)
	{
		int result =qamapper.deleteAttach(attid);
		if(result>0) {
			return true;
		}
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
	
	public OneBoard getQueBoard(int qnum)
	{
		return qamapper.findQueBoard(qnum);
	}
	
	public int deleteQueB(int qnum)
	{
		return qamapper.deleteQueB(qnum);
	}
	
	public boolean deleteAll(int qnum)
	{
		int arow = qamapper.deleteAllF(qnum);
		int brow = qamapper.deleteQueB(qnum);
		if(arow>0 && brow>0) return true;
		return false;
	}
	/* end of Q&A 관련 메소드*/
	
	/*AdminBoard관련 메소드 */
	public PageInfo<Map<String, Object>> noticePage(int pg, int cnt)
	{
		PageHelper.startPage(pg, cnt);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(mapper.noticeList());
		List<Map<String, Object>> mlist = pageInfo.getList();
		
		return pageInfo;
	}

	
	public List<AdminBoard> adminBList(List<Map<String, Object>> mlist)
	{
		List<AdminBoard> list = new ArrayList<>();
		
		for(int i=0; i<mlist.size(); i++)
		{
			Map<String, Object> map= mlist.get(i);

			AdminBoard adminb =parseAdminB(map);
			
			BigDecimal big = (java.math.BigDecimal)map.get("HIT");
			adminb.setHit((big.intValue()));
			list.add(adminb);
		}
		return list;
	}
	
	public AdminBoard parseAdminB( Map<String, Object> map)
	{
		BigDecimal big = (java.math.BigDecimal)map.get("ADNUM");
		
		AdminBoard adminb = new AdminBoard(big.intValue());
		adminb.setTitle((String)map.get("TITLE"));
		adminb.setAuthor((String)map.get("AUTHOR"));
		adminb.setContent((String)map.get("CONTENT"));

		try {
			String jts = String.valueOf(map.get("DATE_ADMIN"));
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			//Date parseDate;
			//System.out.println(map.get("DATE_ADMIN"));
			log.info("날짜:" + dateFormat.format(map.get("DATE_ADMIN"))); 
			String date = dateFormat.format(map.get("DATE_ADMIN"));
			adminb.setDate_admin(date);
			//adminb.setDate_admin(new java.sql.Timestamp(parseDate.getTime()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return adminb;
	}
	public AdminBoard detail_adminb(int adnum)
	{
		List<Map<String, Object>> mlist = mapper.detail_adminb(adnum);
		Map<String, Object> boardMap = mlist.get(0);
		
		AdminBoard adminb =parseAdminB(boardMap);
		
		if(boardMap.get("ATTID")!=null)
		{
			for(int i=0; i<mlist.size(); i++)
			{
				boardMap =mlist.get(i); //새로 boardMap을 해줘야지 안그러면 위에서 한 mlist.get(0)으로만 돌아간다.
			
				AdminAttachBoard att = new AdminAttachBoard();
				
				String attname = (String)boardMap.get("ATTNAME");
				log.info("attname:"+attname);
				att.setAttname(attname);
				java.math.BigDecimal bigd=(java.math.BigDecimal)boardMap.get("ATTSIZE");
				att.setAttsize(bigd.intValue());
				java.math.BigDecimal bigid = (java.math.BigDecimal)boardMap.get("ATTID");
				att.setAttid(bigid.intValue());

				
				adminb.getAttList().add(att);
			}
		}		
		mapper.increaseHit_admin(adnum);
		//oneb =mapper.findQueBoard(num);
		
	//	log.info(content);
		return adminb;
	}
	
	@Transactional
	public boolean addAdmin(HttpServletRequest request, AdminBoard adminb, MultipartFile[] mfiles)
	{
		//log.info("admin svc, mfiles.length={}", mfiles.length);

		ServletContext context =request.getServletContext();
		String savePath = context.getRealPath("/WEB-INF/files");
		
		 List<AdminAttachBoard> alist = new ArrayList<>();
		try {
			//log.info("mfiles length:"+ mfiles.length);
			if(!mfiles[0].isEmpty())//첨부파일 있으면
			{
				for(int i=0; i<mfiles.length; i++) {
						mfiles[i].transferTo(new File(savePath+"/"+mfiles[i].getOriginalFilename()));//첨부파일저장
						
						AdminAttachBoard attb = new AdminAttachBoard();
						attb.setAttname(mfiles[i].getOriginalFilename());
						attb.setAttsize(mfiles[i].getSize());
						alist.add(attb);
						
						int arow =mapper.saveAdminAttach(alist);
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
	public boolean updateAdminB(HttpServletRequest request, AdminBoard adminb, MultipartFile[] mfiles)
	{
		//log.info("svc, mfiles.length={}", mfiles.length);
		ServletContext context =request.getServletContext();
		String savePath = context.getRealPath("/WEB-INF/files");
		List<AdminAttachBoard> alist = new ArrayList<>();
		int brow = mapper.updateAdminB(adminb);
		 
		try {
			boolean uploaded=false;

			if(!mfiles[0].isEmpty())//첨부파일 있으면
			{
				for(int i=0; i<mfiles.length; i++) {
						mfiles[i].transferTo(new File(savePath+"/"+mfiles[i].getOriginalFilename()));//첨부파일저장
						
						AdminAttachBoard attb = new AdminAttachBoard();
						attb.setAttname(mfiles[i].getOriginalFilename());
						attb.setAttsize(mfiles[i].getSize());
						attb.setAdnum(adminb.getAdnum());
						log.info("adnum값"+adminb.getAdnum());
						alist.add(attb);
					//	log.info("svc, attb 목록"+ alist );
	//					return uploaded;
				}
				
				int arow =mapper.moreAttach_admin(alist);
				log.info("svc, arow 값"+arow);
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


	
	public boolean delAdminIndiv(int attid)
	{
		int result =mapper.delAdminAttach(attid);
		if(result>0) {
			return true;
		}
		return false;
	}


	public boolean deleteAll_admin(int adnum)
	{
		int arow = mapper.deleteAllF_admin(adnum);
		int brow = mapper.delAdminB(adnum);
		if(arow>0 && brow>0) return true;
		return false;
	}

	
	public boolean delFromServer_admin(List<AdminAttachBoard> attachList)
	{
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
		List<AttachBoard> attList =qamapper.getAttachList(qnum);
		return attList;
	}

	public AttachBoard getAttach(int attid)
	{
		AttachBoard attach =qamapper.getAttach(attid);
		return attach;
	}

	public List<AdminAttachBoard> getAdminAttachList(int adnum)
	{
		List<AdminAttachBoard> attList =mapper.getAdminAttachList(adnum);
		return attList;
	}
	
	public AdminAttachBoard getAdminAttach(int attid)
	{
		AdminAttachBoard attach =mapper.getAdminAttach(attid);
		return attach;
	}

	public int delAdminB(int adnum)
	{
		return mapper.delAdminB(adnum);
	}
	
	public PageInfo<Map<String, Object>> faqPage(int pg, int cnt)
	{
		PageHelper.startPage(pg, cnt);
		PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(mapper.faqList());
		List<Map<String, Object>> mlist = pageInfo.getList();
		
		return pageInfo;
	}
	
	/*end of AdminBoard관련 메소드 */

}
