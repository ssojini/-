package com.example.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.UserEditMapper;
import com.example.demo.vo.UserJoin;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@Slf4j
public class mypageService {
	@Autowired
	private UserEditMapper map;

	private Path fileStorageLocation;
	
	public List<UserJoin> userlist()
	{
		return map.userlist();
	}
	
	public UserJoin userinfo(String userid)
	{
		System.out.println("useridfo:   "+ map.userinfo(userid));
		return map.userinfo(userid);
		
	}
	
	@Autowired
	  public mypageService(Environment env) 
	  {
	    this.fileStorageLocation = Paths.get("./src/main/resources/static/images/profile")
	        .toAbsolutePath().normalize();
	    try {
	      Files.createDirectories(this.fileStorageLocation);
	    } catch (Exception ex) {
	      throw new RuntimeException(
	          "Could not create the directory where the uploaded files will be stored.", ex);
	    }
	  }
	
	 private String getFileExtension(String fileName) {
		    if (fileName == null) {
		      return null;
		    }
		    String[] fileNameParts = fileName.split("\\.");

		    return fileNameParts[fileNameParts.length - 1];
		  }

	 public boolean storeFile(MultipartFile file, UserJoin userjoin) {
		    // Normalize file name
		 String fileName= null;
		 //map.userinfo(userid).getProfile();
		 //System.out.println("fname:  "+  map.userinfo(userjoin.getUserid()).getProfile());
		 if(file.isEmpty())
		 {
			 fileName = map.userinfo(userjoin.getUserid()).getProfile();
		 }
		 else {			 
			 String originalFileName = file.getOriginalFilename();	//오리지날 파일명
			 String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); //파일 확장자

			 fileName = UUID.randomUUID() + extension;	//저장될 파일 명
			 //fileName = file.getOriginalFilename();
		  
		    try {
		      // Check if the filename contains invalid characters
		      if (fileName.contains("..")) {
		        throw new RuntimeException(
		            "Sorry! Filename contains invalid path sequence " + fileName);
		      }

		      Path targetLocation = this.fileStorageLocation.resolve(fileName);
		      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

		      //return fileName;
		    } catch (IOException ex) {
		      throw new RuntimeException("Could not store file " + fileName + ". Please try again!", ex);
		    }
		 }
		    
		    String email1 = userjoin.getEmail1();
			String email2 = userjoin.getEmail2();
			String email = email1 + "@" + email2;
			
			userjoin.setEmail(email);
			userjoin.setProfile("/images/profile/"+fileName);

			int edit =  map.useredit(userjoin);
			
			boolean editted = false;
			if(edit>0)
			{
				editted =true;
			}
			return editted;
		  }

	
	public boolean deleteuser(UserJoin userjoin)
	{
		String db_pwd = map.userinfo(userjoin.getUserid()).getPwd();

		boolean deleted=false;
		if(db_pwd.equals(userjoin.getPwd()))
		{
			int delete = map.deleteuser(userjoin.getUserid());
			if(delete>0)
			{
				deleted=true;
			}
		}
		return deleted;
	}
	
	public boolean changepwd(UserJoin userjoin)
	{
		boolean changed= false;
		if(map.changepwd(userjoin)>0)
		{
			changed= true;
		}
		return changed;
	}
	
}
