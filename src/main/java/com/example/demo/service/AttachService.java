package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.interfaces.AttachRepository;
import com.example.demo.vo.Attach;

@Service
public class AttachService {
	@Autowired
	private AttachRepository repo;

	public Attach save(Integer fbnum, String aname, Long asize) {
		Attach attach = new Attach();
		attach.setFbnum(fbnum);
		attach.setAname(aname);;
		attach.setAsize(asize);
		
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
}
