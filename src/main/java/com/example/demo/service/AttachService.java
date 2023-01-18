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

	public List<Attach> deleteByFbnum(HttpServletRequest request, Integer fbnum) {
		List<Attach> listAttach = attachRepository.deleteByFbnum(fbnum);
		deleteFiles(request, listAttach);
		return listAttach;
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

	public List<Map<String,String>> listAttachToListMap(List<Attach> listAttach) {
		List<Map<String,String>> liMap = new ArrayList<>();
		for (int i = 0; i < listAttach.size(); i++) {
			Map<String,String> map = new HashMap<>();
			map.put("aname", listAttach.get(i).getAname());
			map.put("anum", ""+listAttach.get(i).getAnum());
			map.put("asize", ""+listAttach.get(i).getAsize());
			map.put("fbnum", ""+listAttach.get(i).getFbnum());
			liMap.add(map);
		}
		return liMap;
	}

	public boolean delete(HttpServletRequest request, List<Attach> listAttach) {
		for (int i = 0; i < listAttach.size(); i++) {
			attachRepository.deleteById(listAttach.get(i).getAnum());
			deleteFile(request, listAttach.get(i));
		}
		return true;
	}

	public boolean deleteFiles(HttpServletRequest request, List<Attach> listAttach) {
		for (int i = 0; i < listAttach.size(); i++) {
			deleteFile(request, listAttach.get(i));
		}
		return true;
	}

	public boolean deleteFile(HttpServletRequest request, Attach attach) {
		try {
			String path = request.getServletContext().getRealPath("/WEB-INF/files");
			new File(path + "/" + attach.getAnum() + "_" + attach.getAname()).delete();
			return true;
		} catch(Exception e) {
			System.err.println("파일삭제 실패");
			e.printStackTrace();
		}
		return false;
	}

	public List<Attach> jsonArrToListAttach(String data) {
		JSONParser jsPar = new JSONParser();
		try {
			JSONArray jsArr = (JSONArray)jsPar.parse(data);

			List<Attach> listAttach = new ArrayList<>();
			for (int i = 0; i < jsArr.size(); i++) {
				JSONObject json = (JSONObject)jsArr.get(i);
				System.out.println(json.toJSONString());

				Attach attach = new Attach();
				attach.setFbnum(Integer.valueOf((String)json.get("fbnum")));
				attach.setAnum(Integer.valueOf((String)json.get("anum")));
				attach.setAname((String)json.get("aname"));
				attach.setAsize(Long.valueOf((String)json.get("asize")));

				listAttach.add(attach);
			}
			return listAttach;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
