<<<<<<< HEAD
package com.example.demo.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.AttachBoard;
import com.example.demo.vo.OneBoard;

@Mapper
public interface AdminBoardMapper 
{		
	public List<Map<String, Object>> QAlist();
	public int addQueBoard(OneBoard oneb);	
//	public int saveAttach(List<AttachBoard> alist);
	public int addAnsBoard(OneBoard oneb);
	public int saveAttach(AttachBoard attb);

}
=======
package com.example.demo.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.AttachBoard;
import com.example.demo.vo.OneBoard;

@Mapper
public interface AdminBoardMapper 
{		
	public List<Map<String, Object>> QAlist();
	public int addQueBoard(OneBoard oneb);	
//	public int saveAttach(List<AttachBoard> alist);
	public int addAnsBoard(OneBoard oneb);
	public int saveAttach(AttachBoard attb);

}
>>>>>>> branch 'master' of https://github.com/pastebean/EzenFinal.git
