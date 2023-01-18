package com.example.demo.service;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.interfaces.AttachRepository;
import com.example.demo.vo.Attach;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AttachService {
	@Autowired
	private AttachRepository attachRepository;
	@Autowired
	private ResourceLoader resourceLoader;

	public Attach save(Integer fbnum, String aname, Long asize) {
		Attach attach = new Attach();
		attach.setFbnum(fbnum);
		attach.setAname(aname);;
		attach.setAsize(asize);
		
		return attachRepository.save(attach);
	}
	
	public Attach save(Attach attach) {
		return attachRepository.save(attach);
	}
	
	public List<Attach> getList(Integer fbnum) {
		List<Attach> listAttach = attachRepository.findAllByFbnum(fbnum);
		return listAttach;
	}

	public List<Attach> deleteByFbnum(Integer fbnum) {
		return attachRepository.deleteByFbnum(fbnum);
	}

	public boolean deleteByAnum(Integer anum) {
		attachRepository.deleteById(anum);
		return true;
	}
	
	public ResponseEntity<Resource> donwload(HttpServletRequest request, String filepath) {
		String path = request.getServletContext().getRealPath("/WEB-INF/files");
		Resource resource = resourceLoader.getResource(path + filepath);
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (contentType == null) {
			contentType = "application/octet-stream";
		}
		String filename = resource.getFilename();
		filename = filename.substring(filename.indexOf("_") + 1, filename.length());
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"").body(resource);
	}
	
	public List<Attach> upload(HttpServletRequest request, MultipartFile[] files, Integer fbnum) {
		String savePath = request.getServletContext().getRealPath("/WEB-INF/files");
		System.out.println("savePath:"+savePath);
		try {
			List<Attach> liAttach = new ArrayList<>();
			for (int i = 0; i < files.length; i++) {
				Attach attach = new Attach();
				attach.setAname(files[i].getOriginalFilename());
				attach.setAsize(files[i].getSize());
				attach.setFbnum(fbnum);
				Attach save = attachRepository.save(attach);
				
				files[i].transferTo(new File(savePath + "/" + save.getAnum() + "_" + save.getAname()));
				
				liAttach.add(save);
			}
			return liAttach;
		} catch (Exception e) {
			System.err.println("파일저장 실패");
			//e.printStackTrace();
		}
		return null;
	}
	
	public List<Map<String,String>> liAttachToLiMap(List<Attach> liAttach) {
		List<Map<String,String>> liMap = new ArrayList<>();
		for (int i = 0; i < liAttach.size(); i++) {
			Map<String,String> map = new HashMap<>();
			map.put("aname", liAttach.get(i).getAname());
			map.put("anum", ""+liAttach.get(i).getAnum());
			map.put("asize", ""+liAttach.get(i).getAsize());
			map.put("fbnum", ""+liAttach.get(i).getFbnum());
			liMap.add(map);
		}
		return liMap;
	}
	
	public boolean delete(HttpServletRequest request, Attach...attachs) {
		try {
			String path = request.getServletContext().getRealPath("/WEB-INF/files");
			System.out.println("path:"+path);
			for (int i = 0; i < attachs.length; i++) {
				attachRepository.deleteById(attachs[i].getAnum());
				new File(path + "/" + attachs[i].getAnum() + "_" + attachs[i].getAname()).delete();
			}
			return true;
		} catch(Exception e) {
			System.err.println("파일삭제 실패");
			e.printStackTrace();
		}
		return false;
	}
	
	public Attach[] jsonArrToArrAttach(String data) {
		JSONParser jsPar = new JSONParser();
		try {
			JSONArray jsArr = (JSONArray)jsPar.parse(data);
			
			Attach[] arrAttach = new Attach[jsArr.size()];
			for (int i = 0; i < jsArr.size(); i++) {
				JSONObject json = (JSONObject)jsArr.get(i);
				System.out.println(json.toJSONString());
				
				Attach attach = new Attach();
				attach.setFbnum(Integer.valueOf((String)json.get("fbnum")));
				attach.setAnum(Integer.valueOf((String)json.get("anum")));
				attach.setAname((String)json.get("aname"));
				attach.setAsize(Long.valueOf((String)json.get("asize")));
				
				arrAttach[i] = attach;
			}
			return arrAttach;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
