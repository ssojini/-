package com.example.demo.service;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.UserEditMapper;
import com.example.demo.vo.Freeboard;
import com.example.demo.vo.Main_Title;
import com.example.demo.vo.User;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
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
	
	public List<User> userlist()
	{
		return map.userlist();
	}
	
	public User userinfo(String userid)
	{
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

	 public boolean storeFile(MultipartFile file, User user) {
		    // Normalize file name
		 String fileName= null;
		 //map.userinfo(userid).getProfile();
		 //System.out.println("fname:  "+  map.userinfo(user.getUserid()).getProfile());
		 if(file.isEmpty())
		 {
			 fileName = map.userinfo(user.getUserid()).getProfile();
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
		    
		    String email1 = user.getEmail1();
			String email2 = user.getEmail2();
			String email = email1 + "@" + email2;
			

			user.setEmail(email);
			user.setProfile("/images/profile/"+fileName);


			int edit =  map.useredit(user);
			
			boolean editted = false;
			if(edit>0)
			{
				editted =true;
			}
			return editted;
		  }

	
	public boolean deleteuser(User user)
	{
		String db_pwd = map.userinfo(user.getUserid()).getPwd();

		boolean deleted=false;
		if(db_pwd.equals(user.getPwd()))
		{
			int delete = map.deleteuser(user.getUserid());
			if(delete>0)
			{
				deleted=true;
			}
		}
		return deleted;
	}
	
	public boolean changepwd(String userid, String pwd)
	{
		boolean changed= false;
		if(map.changepwd(userid,pwd)>0)
		{
			changed= true;
		}
		return changed;
	}
	
	public List<Freeboard> getmyboard(String userid)
	{
		List<Freeboard>myboard= map.getmyboard(userid);
		return myboard;
	}

	public Main_Title mainTitle() {
		Random rd = new Random();
		return map.maintitle(rd.nextInt(20)+1);
	}
	
}
