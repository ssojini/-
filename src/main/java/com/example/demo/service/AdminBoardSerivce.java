package com.example.demo.service;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.example.demo.mapper.AdminBoardMapper;
import com.example.demo.vo.AttachBoard;
import com.example.demo.vo.OneBoard;

@Service
public class AdminBoardSerivce 
{
	@Autowired
	private AdminBoardMapper mapper;
	public List<OneBoard> list(Model m)
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
		
		return list;
	}
}
