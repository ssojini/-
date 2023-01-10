package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
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
		map.put("result", fbs.addFreeBoard(session,freeBoard) != null ? "저장 성공" : "저장 실패");
		return map;
	}
	
	@PostMapping("/uploadFiles")
	@ResponseBody
	public Map<String,Object> uploadFiles(HttpServletRequest request, Model m, MultipartFile[] files) {
		Map<String,Object> map = new HashMap<>();
		System.out.println("files:"+files);
		boolean result = as.saveAttach(request, files);
		map.put("result", result);
		return map;
	}

	@GetMapping("/detailFreeBoard")
	public String detailFreeBoard(Model m, Integer fbnum) {
		FreeBoard freeBoard = fbs.getFreeBoardByFbnum(fbnum);
		System.out.println("freeBoard:"+freeBoard);
		m.addAttribute("freeBoard",freeBoard);
		return "html/freeboard/detailFreeBoard";
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

	@GetMapping("/test1")
	public String test1() {
		return "html/mypage/test.html";
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

	@GetMapping("/writeAdmin")
	public String writeAdmin()
	{

		return "html/admin/writeBoard_admin";
	}

	@GetMapping("/qaList")
	public String qaList(Model m)
	{
		m.addAttribute("list", absvc.qList());
		log.info("컨트롤러 리스트"+ absvc.qList());
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
	public Map<String, Object> writeQueB(HttpServletRequest request, OneBoard oneb, MultipartFile[] mfiles)
	{
		boolean uploaded = absvc.uploadQueB(request, oneb, mfiles);
		Map<String, Object> map = new HashMap<>();
		map.put("uploaded", uploaded);
		return map;
	}

	@GetMapping("/bmi")
	public String bmi()
	{
		return "hrml/mypage/BMI_cul";
	}

}