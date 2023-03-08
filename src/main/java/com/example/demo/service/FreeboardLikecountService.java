package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.FreeboardLikecountRepository;
import com.example.demo.vo.FreeboardLikecount;

@Service
public class FreeboardLikecountService {
	@Autowired
	private FreeboardLikecountRepository repo;

	public boolean changeLikecount(Integer fbnum, String nickname) {
		if(repo.findByPnumAndNickname(fbnum, nickname) == null) {
			FreeboardLikecount likecount = new FreeboardLikecount();
			likecount.setPnum(fbnum);
			likecount.setNickname(nickname);
			repo.save(likecount);
			return true;
		} else {
			repo.deleteByPnumAndNickname(fbnum, nickname);
			return false;
		}
	}
	
	public List<FreeboardLikecount> getCount(Integer pnum) {
		return repo.findByPnum(pnum);
	}
	
	public boolean isLikecountUser(Integer pnum, String nickname) {
		return repo.findByPnumAndNickname(pnum, nickname)!=null;
	}
}
