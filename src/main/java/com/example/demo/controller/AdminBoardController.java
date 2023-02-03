package com.example.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.service.AdminBoardSerivce;
import com.example.demo.vo.AdminAttachBoard;
import com.example.demo.vo.AdminBoard;
import com.example.demo.vo.OneBoard;
import com.github.pagehelper.PageInfo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminBoardController 
{
	@Autowired 
	private AdminBoardSerivce absvc;

	@GetMapping("/qaList/{pg}/{cnt}") 
	public String qaList(Model m, @PathVariable int pg, @PathVariable int cnt)
	{
		PageInfo<Map<String, Object>> pageInfo =  absvc.getPage(pg, cnt);
		List<OneBoard> list = absvc.qaList(pageInfo.getList());
		m.addAttribute("list", list);
		
		return "html/admin/qaList";
	}
	
	@GetMapping("/add")
	public String addAdminForm(Model m, String name)
	{
		m.addAttribute("name", name);
		m.addAttribute("userid", "관리자");
		return "html/admin/add";
	}
	
	@PostMapping("/add")
	@ResponseBody
	public Map<String,Object> addAdmin(HttpServletRequest request,Model m, 
			AdminBoard adminb, @RequestParam("attach") MultipartFile[] mfiles) {
		
		boolean added = absvc.addAdmin(request, adminb, mfiles);
		log.info("name값:"+adminb.getName());
		Map<String, Object> map = new HashMap<>();
		map.put("added", added);
		log.info("불리언 값:"+added);
		return map;
	}	
	
	@GetMapping("/detailByQnum/{qnum}")
	public String detailByQnum(@PathVariable("qnum") int qnum, Model m, HttpSession session)
	{
		OneBoard oneb = absvc.detailByQnum(qnum);
		m.addAttribute("oneb", oneb);
		String userid =(String)session.getAttribute("userid");
		m.addAttribute("userid", userid);
		return "html/admin/detail_q_admin";
	}
	
	@GetMapping("/edit_q/{num}")
	public String editTestForm(@PathVariable("num") int num, Model m, HttpSession session)
	{
		OneBoard oneb = absvc.detailByQnum(num);
		m.addAttribute("oneb", oneb);
		m.addAttribute("qnum", num);
		String userid = (String)session.getAttribute("userid");
		m.addAttribute("userid", userid);
		return "html/admin/edit_q_admin";
	}
	
	@PostMapping("/edit_q/{qnum}")
	@ResponseBody
	public Map<String, Boolean> updateQueB(	@PathVariable("qnum") int qnum,
			OneBoard oneb,
			@RequestParam("attach") MultipartFile[] mfiles,
			HttpServletRequest request)
	{
		boolean uploaded = absvc.updateQueB(request, oneb, mfiles);
		Map<String, Boolean> map = new HashMap<>();
		map.put("uploaded", uploaded);
		return map;
	}
	
	
	@GetMapping("/notice/{pg}/{cnt}") 
	public String notice(Model m, @PathVariable int pg, @PathVariable int cnt)
	{
		PageInfo<Map<String, Object>> pageInfo = absvc.noticePage(pg, cnt);
		List<AdminBoard> list = absvc.adminBList(pageInfo.getList());
		m.addAttribute("list", list);
		
		return "html/admin/notice_admin";
	}
	
	@GetMapping("/detail_notice/{adnum}")
	public String detail_notice(@PathVariable("adnum") int adnum, Model m)
	{
		AdminBoard noticeb = absvc.detail_adminb(adnum);
		m.addAttribute("noticeb", noticeb);
		return "html/admin/detail_notice";
	}
	
	@GetMapping("/edit_notice/{adnum}")
	public String editNotice(@PathVariable("adnum") int adnum, Model m)
	{
		AdminBoard adminb = absvc.detail_adminb(adnum);
		m.addAttribute("adminb", adminb);
		m.addAttribute("adnum", adnum);
		return "html/admin/edit_notice";
	}
	
	@PostMapping("/edit_notice/{adnum}")
	@ResponseBody
	public Map<String, Boolean> updateNotice(@PathVariable("adnum") int adnum,
			AdminBoard adminb,
			@RequestParam("attach") MultipartFile[] mfiles,
			HttpServletRequest request)
	{
		boolean uploaded = absvc.updateAdminB(request, adminb, mfiles);
		Map<String, Boolean> map = new HashMap<>();
		map.put("uploaded", uploaded);
		return map;
	}
	
	@PostMapping("/delAdminIndiv")
	@ResponseBody
	public Map<String, Object> delAdminIndiv(@RequestParam("attid") int attid)
	{
		AdminAttachBoard attach = absvc.getAdminAttach(attid);
		
		boolean result= absvc.delAdminIndiv(attid);	// DB에서 지우기
		//log.info("ctrl, result 값:"+result);
		//return result.toString();
		
		boolean serverDeletion = deleteAdminFile(attach);// 서버에서 지우기
		//log.info("ctrl, deletion값:"+ serverDeletion);
		boolean trueDeletion = (result == serverDeletion);
		Map<String, Object> map = new HashMap<>();
		map.put("deleted", trueDeletion);
		
		return map;
	}
	
	
	public boolean deleteAdminFile(AdminAttachBoard attach)
	{
		if(attach==null)
		{
			return false;
		}
		else {	
			Path file =Paths.get("src/main/webapp/WEB-INF/files");
			file= file.resolve(attach.getAttname());
			
			file = file.toAbsolutePath();
			boolean check=false;
			try {
				check = Files.deleteIfExists(file);
								
				String abpath = file.toString();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return check;
		}
	}
	
	@PostMapping("/delAdminB")
	@ResponseBody
	public Map<String, Boolean> delNotice(
			@RequestParam ("adnum") int adnum)
	{			
		Map<String, Boolean> map = new HashMap<>();			
		List<AdminAttachBoard> attList =absvc.getAdminAttachList(adnum);

		boolean delOnServer= false;
		boolean delOnDB= false;
		if(attList!=null)// 첨부파일이 있으면
		{
			delOnServer =absvc.delFromServer_admin(attList);
			delOnDB = absvc.deleteAll_admin(adnum);
			map.put("deleted", ((delOnServer=true)&&(delOnDB=true)));
			
		}else {// 첨부파일이 없으면
			int row =absvc.delAdminB(adnum);
			map.put("deleted", row>0);
		}	
					
		return map;
	}

	@GetMapping("/faq/{pg}/{cnt}") 
	public String faq(Model m, @PathVariable int pg, @PathVariable int cnt)
	{
		PageInfo<Map<String, Object>> pageInfo = absvc.faqPage(pg, cnt);
		List<AdminBoard> list = absvc.adminBList(pageInfo.getList());
		m.addAttribute("list", list);
		
		return "html/admin/faq_admin";
	}
	
	@GetMapping("/detail_faq/{adnum}")
	public String detail_faq(@PathVariable("adnum") int adnum, Model m)
	{
		AdminBoard faqb = absvc.detail_adminb(adnum);
		m.addAttribute("faqb", faqb);
		return "html/admin/detail_faq_admin";
	}
	
	@GetMapping("/edit_faq/{adnum}")
	public String editFaq(@PathVariable("adnum") int adnum, Model m)
	{
		AdminBoard adminb = absvc.detail_adminb(adnum);
		m.addAttribute("adminb", adminb);
		m.addAttribute("adnum", adnum);
		return "html/admin/edit_faq_admin";
	}
	
	@PostMapping("/edit_faq/{adnum}")
	@ResponseBody
	public Map<String, Boolean> updateFaq(@PathVariable("adnum") int adnum,
			AdminBoard adminb,
			@RequestParam("attach") MultipartFile[] mfiles,
			HttpServletRequest request)
	{
		boolean uploaded = absvc.updateAdminB(request, adminb, mfiles);
		Map<String, Boolean> map = new HashMap<>();
		map.put("uploaded", uploaded);
		return map;
	}
	
	
	
	
}
