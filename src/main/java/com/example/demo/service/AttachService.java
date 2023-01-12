package com.example.demo.service;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
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
	private AttachRepository repo;
	@Autowired
	private ResourceLoader resourceLoader;

	private final Path fileStorageLocation;

	@Autowired
	public AttachService(Environment env) 
	{
		this.fileStorageLocation = Paths.get("./src/main/resources/static/images/freeboard")
				.toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.fileStorageLocation);
		} catch (Exception ex) {
			throw new RuntimeException(
					"Could not create the directory where the uploaded files will be stored.", ex);
		}
	}

	public boolean saveAttach(HttpServletRequest request, MultipartFile[] files, Integer fbnum) {
		//String savePath = request.getServletContext().getRealPath("../resources/WEB-INF/files/");
		String savePath = fileStorageLocation.toUri().getPath();
		try {
			for (int i = 0; i < files.length; i++) {
				String fname = files[i].getOriginalFilename();
				long fsize = files[i].getSize();
				Attach attach = new Attach();
				attach.setFbnum(fbnum);
				attach.setAname(fname);
				attach.setAsize(fsize);
				Attach saveAttach = repo.save(attach);
				System.out.println("fileStorageLocation.toUri().getPath():"+fileStorageLocation.toUri().getPath());
				files[i].transferTo(new File(savePath + "/" + saveAttach.getAnum() + "_" + saveAttach.getAname()));
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public ResponseEntity<Resource> donwloadAttach(HttpServletRequest request, Integer fbnum, String aname) {
		Resource resource = resourceLoader.getResource("../resources/static/images/freeboard/"+fbnum + "_" + aname);
		System.out.println("resource:"+resource);
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

	public List<Attach> getListAttach(Integer fbnum) {
		List<Attach> listAttach = repo.findAllByFbnum(fbnum);
		return listAttach;
	}
	
	public boolean deleteAttach(Integer fbnum) {
		repo.deleteByFbnum(fbnum);
		return true;
	}
}
