package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
	private AttachRepository repo;
	@Autowired
	private FileService fileService;

	public Attach save(Integer fbnum, String aname, Long asize) {
		Attach attach = new Attach();
		attach.setFbnum(fbnum);
		attach.setAname(aname);;
		attach.setAsize(asize);
		
		return repo.save(attach);
	}
	
	public Attach save(Attach attach) {
		return repo.save(attach);
	}
	
	public List<Attach> getList(Integer fbnum) {
		List<Attach> listAttach = repo.findAllByFbnum(fbnum);
		return listAttach;
	}

	public List<Attach> deleteByFbnum(Integer fbnum) {
		return repo.deleteByFbnum(fbnum);
	}

	public boolean deleteByAnum(Integer anum) {
		repo.deleteById(anum);
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
				Attach save = ar.save(attach);
				
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
		fileService.delete(request, attachs);
		return false;
	}
}
