package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.interfaces.FreeboardLikecountRepository;
import com.example.demo.vo.FreeboardLikecount;

@Service
public class FreeboardLikecountService {
	@Autowired
	private FreeboardLikecountRepository repo;

	public void changeLikecount(Integer fbnum, String nickname) {
		if(repo.findByNickname(nickname) == null) {
			FreeboardLikecount likecount = new FreeboardLikecount();
			likecount.setPnum(fbnum);
			likecount.setNickname(nickname);
			repo.save(likecount);
		} else {
			repo.deleteByPnumAndNickname(fbnum, nickname);
		}
	}
}
