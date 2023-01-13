package com.example.demo.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
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
import com.example.demo.service.AttachService;
import com.example.demo.service.FileStorageService;
import com.example.demo.service.FreeBoardService;
import com.example.demo.service.mypageService;
import com.example.demo.vo.AdminBoard;
import com.example.demo.vo.Attach;
import com.example.demo.vo.AttachBoard;
import com.example.demo.vo.FreeBoard;
import com.example.demo.vo.OneBoard;
import com.example.demo.vo.UserJoin;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/health")
@Slf4j
public class HealthController {
	@Autowired HttpSession session;

	@Autowired
	private FileStorageService fss;
	
	/* 다루한 */
	@Autowired
	private FreeBoardService fbs;
	@Autowired
	private AttachService as;

	@GetMapping("/freeBoard")
	public String freeBoard(Model m, String bname) {
		List<FreeBoard> listFreeBoard = fbs.getFreeBoardList(bname);
		m.addAttribute("listFreeBoard", listFreeBoard);
		m.addAttribute("bname",bname);
		return "html/freeboard/freeBoard";
	}

	@PostMapping("/getListMap")
	@ResponseBody
	public List<Map<String, Object>> getListMap(Model m, String bname) {
		List<Map<String, Object>> listMap = fbs.getListFreeBoardToListMap(bname);
		return listMap;
	}

	@GetMapping("/addFreeBoard")
	public String addFreeBoard(Model m, String bname) {
		m.addAttribute("bname", bname);
		return "html/freeboard/addFreeBoard";
	}
	@PostMapping("/addFreeBoard")
	@ResponseBody
	public Map<String,Object> addFreeBoard(Model m, FreeBoard freeBoard) {
		Map<String,Object> map = new HashMap<>();
		FreeBoard addFreeBoard = fbs.addFreeBoard(session,freeBoard);
		map.put("result", addFreeBoard!=null?"true":"false");
		map.put("freeBoard", addFreeBoard);
		return map;
	}
	
	@PostMapping("/uploadFiles")
	@ResponseBody
	public Map<String,Object> UploadFiles(HttpServletRequest request, Model m, MultipartFile[] files, Integer fbnum, String contents) {
		Map<String,Object> map = new HashMap<>();
		boolean result = as.saveAttach(request, files, fbnum);
		FreeBoard updateFreeBoard = fbs.updateContents(fbnum, contents);
		map.put("result", result);
		return map;
	}
	@GetMapping("/downloadFile")
	public ResponseEntity<Resource> donwloadFile(HttpServletRequest request, Integer fbnum, String aname) {
		return as.donwloadAttach(request, fbnum, aname);
	}

	@GetMapping("/detailFreeBoard")
	public String detailFreeBoard(Model m,Integer fbnum) {
		FreeBoard freeBoard = fbs.getFreeBoardByFbnum(fbnum);
		m.addAttribute("freeBoard",freeBoard);
		return "html/freeboard/detailFreeBoard";
	}
	
	@PostMapping("/deleteFreeBoard")
	@ResponseBody
	public Map<String,Object> deleteFreeBoard(Model m, Integer fbnum) {
		Map<String,Object> map = new HashMap<>();
		map.put("result",fbs.deleteFreeBoard(fbnum)&&as.deleteAttach(fbnum));
		return map;
	}
	
	@GetMapping("/editFreeBoard")
	public String editFreeBoard(Model m, Integer fbnum) {
		m.addAttribute("freeBoard",fbs.getFreeBoardByFbnum(fbnum));
		m.addAttribute("listAttach", as.getListAttach(fbnum));
		return "html/freeBoard/editFreeBoard";
	}

	/* 다루한 */

	/* 현주 */
	@Autowired
	private mypageService mp_svc;

	@Autowired
	ResourceLoader resourceLoader;

	@GetMapping("/")
	@ResponseBody
	public String userlist() {
		return mp_svc.userlist().toString();
	}

	@GetMapping("/calorie")
	public String cal()
	{
		return "health/calorie";
	}

	@PostMapping("/cal")
	@ResponseBody
	public Map<String,Object> calculate(int height, int gender, int active)
	{
		Map<String, Object> map = new HashMap<>();
		float recommand = (float) ( (height-100)*0.9*((gender*5)+20) ); 
		//System.err.println(recommand+" Kcal");
		map.put("recommand",recommand);

		return map;
	}

	@GetMapping("/useredit/{userid}")
	public String addboardform(@PathVariable(value = "userid", required = false) String userid, Model m) {
		m.addAttribute("user", mp_svc.userinfo(userid));
		return "html/mypage/EditUser";
	}

	@PostMapping("/userEdit")
	@ResponseBody

	public Map<String,Object> useredit(@RequestParam("file")MultipartFile mfiles, 
			HttpServletRequest request, UserJoin userjoin) 
	{
		Map<String,Object> map= new HashMap<>();
		System.out.println("SYSTEM:  "+mp_svc.storeFile(mfiles, userjoin));
		map.put("edited", mp_svc.storeFile(mfiles,userjoin));

		return map;
	}

	@GetMapping("/deleteuser/{userid}")
	public String deleteuser_form(@PathVariable(value = "userid", required = false) String userid, Model m) {
		m.addAttribute("user", mp_svc.userinfo(userid));
		return "html/mypage/DeleteUser";
	}

	@GetMapping("/deleteuser_check/{userid}")
	public String deleteuser_check(@PathVariable(value = "userid", required = false) String userid, Model m) {
		m.addAttribute("user", mp_svc.userinfo(userid));
		return "html/mypage/DeleteUser_Check";
	}


	@PostMapping("/deleteuser")
	@ResponseBody
	public Map<String, Object> deleteuser(@PathVariable(value = "userid", required = false) String userid,  UserJoin userjoin, Model m) {
		m.addAttribute("user", mp_svc.userinfo(userid));
		Map<String, Object>map = new HashMap<>();
		map.put("deleted", mp_svc.deleteuser(userjoin));
		return map;
	}

	@GetMapping("/user_addinfo/{userid}")
	public String useraddinfo(@PathVariable(value = "userid", required = false) String userid, Model m) {
		m.addAttribute("user", mp_svc.userinfo(userid));
		return "html/mypage/UserDetail";
	}

	@GetMapping("/findpwd/{userid}")
	public String findpwd(@PathVariable(value = "userid", required = false) String userid, Model m) {
		m.addAttribute("user", mp_svc.userinfo(userid));
		return "html/mypage/FindPwd";
	}

	@PostMapping("/findpwd/{userid}")
	public String changepwd(@PathVariable(value = "userid", required = false) String userid, Model m) {
		m.addAttribute("user", mp_svc.userinfo(userid));
		return "html/mypage/FindPwd";
	}


	/* 현주 */

	/* 종빈 */
	@GetMapping("/test")
	public String test(Model m) {
		return "html/test/test";
	}

	/* 종빈 */
	@GetMapping("/main")
	public String main1(Model m) {
		return "html/mainPage";
	}
	/* 종빈 */

	/*엘라 */
	@Autowired 
	private AdminBoardSerivce absvc;
	
	@GetMapping("/admin")
	public String admin()
	{
		return "html/admin/adminBoard";
	}

	@GetMapping("/addAdmin")
	public String addAdminForm(Model m, String name)
	{
		m.addAttribute("name", name);
		m.addAttribute("userid", "관리자");
		return "html/admin/writeBoard_admin";
	}
	
	@PostMapping("/addAdmin")
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
	
	@GetMapping("/qaList") //1:1 문의 리스트
	public String qaList(Model m)
	{
		m.addAttribute("list", absvc.qList());
	//	log.info("컨트롤러 리스트"+ absvc.qList());
		return "html/admin/adminBoard";
	}
	
	@GetMapping("/writeQueB")
	public String writeQueBForm(HttpSession session, Model m) 
	{
		session.setAttribute("userid", "smith");		//임의로 하드코딩 한 id
		String id =(String)session.getAttribute("userid");
		m.addAttribute("userid", id);
		return "html/admin/writeQueB";
	}
	
	@PostMapping("/writeQueB")
	@ResponseBody
	public Map<String, Object> writeQueB(HttpServletRequest request, 
			OneBoard oneb, @RequestParam("attach") MultipartFile[] mfiles)
	{
		//log.info("ctl, mfiles.length={}", mfiles.length);
		boolean uploaded = absvc.uploadQueB(request, oneb, mfiles);
		Map<String, Object> map = new HashMap<>();
		map.put("uploaded", uploaded);
		return map;
	}
	
	@GetMapping("/detailByQnum/{num}")
	public String detailByQnum(@PathVariable("num") int num, Model m)
	{
		OneBoard oneb = absvc.detailByQnum(num);
		m.addAttribute("oneb", oneb);
		log.info("oneb에서 나오는 첨부파일:"+oneb.getAttList());
		return "html/admin/detail_q";
	}
	
	@GetMapping("/editQueB/{num}")
	public String editQueBForm(@PathVariable("num") int num, Model m)
	{
		OneBoard oneb = absvc.detailByQnum(num);
		m.addAttribute("oneb", oneb);
		return "html/admin/editQueB";
	}
	
	@PostMapping("/editQueB/{num}")
	@ResponseBody
	public Map<String, Object> editQueB(@RequestParam("oneb") OneBoard oneb, @RequestParam("qnum") int qnum, Model m)
	{
		boolean updated = absvc.updateQueB(oneb, qnum);
		Map<String, Object> map = new HashMap<>();
		map.put("updated", updated);
		
		return map;
	}
	
	@PostMapping("/removeFiles")
	public String remove(@RequestParam("attid") int attid, @RequestParam("qnum") int qnum)
	{
		log.info("remove:" + attid);
		List<AttachBoard> attachList = absvc.getAttachList(qnum);
		if(absvc.deleteAttach(attid))
		{
			deleteFiles(attachList); 
		}
	return "html/admin/adminBoard";
	}
/*	
	public void deleteFiles(List<AttachBoard> attachList)
	{
		if(attachList==null || attachList.size()==0) return;
		log.info("컨트롤러 첨부파일 리스트"+attachList);

		attachList.forEach(attach ->{
			try{	
				Path file= Paths.get("WEB-INF/files");
				file =file.resolve(attach.getAttname());
				Files.deleteIfExists(file);
			}catch (Exception e){
				log.error("Delete file error: "+e.getMessage());
			}
		});
	}
*/
	public void deleteFiles(List<AttachBoard> attachList)
	{
		if(attachList==null || attachList.size()==0) return;
		log.info("컨트롤러 첨부파일 리스트"+attachList);

		attachList.forEach(attach ->{
			try{	
				Path file= Paths.get("WEB-INF/files");
				file =file.resolve(attach.getAttname());
				Files.deleteIfExists(null);
			}catch (Exception e){
				log.error("Delete file error: "+e.getMessage());
			}
		});
	}
	//첨부파일 삭제 테스트
	@GetMapping("/editTest/{num}")
	public String editTestForm(@PathVariable("num") int num, Model m)
	{
		OneBoard oneb = absvc.detailByQnum(num);
		m.addAttribute("oneb", oneb);
		m.addAttribute("qnum", num);
		return "html/admin/editTest";
	}
	
	@PostMapping("/editTest")
	@ResponseBody
	public String editTest(@RequestParam("attid") List<Integer> attidList, Model m)
	{
		int attid =0;
		for(int i=0; i<attidList.size();i++) {
			attid =attidList.get(i);
		}
	//	deleteFiles();
		String result= absvc.deleteTest(attid);
		return result.toString();

	}
	
	/* 엘라 */

	@GetMapping("/bmi")
	public String bmi()
	{
		return "html/mypage/BMI_cul";
	}

}
