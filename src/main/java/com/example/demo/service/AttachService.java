package com.example.demo.service;

import java.io.File;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.interfaces.AttachRepository;
import com.example.demo.vo.Attach;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AttachService {
	@Autowired
	private AttachRepository repo;
	
	public boolean saveAttach(HttpServletRequest request, MultipartFile[] files) {
		String savePath = request.getServletContext().getRealPath("WEB-INF/files/");
		System.out.println("savePath:"+savePath);
		try {
			for (int i = 0; i < files.length; i++) {
				String fname = files[i].getOriginalFilename();
				long fsize = files[i].getSize();
				Attach attach = new Attach();
				attach.setAname(fname);
				attach.setAsize(fsize);
				Attach saveAttach = repo.save(attach);
				files[i].transferTo(new File(savePath + "/" + saveAttach.getAnum() + "_" + saveAttach.getAname()));
			}
			return true;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void getAttach() {
		
	}
}
