package com.example.demo.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.FreeboardLikecount;

public interface FreeboardLikecountRepository extends JpaRepository<FreeboardLikecount, Integer> {
	public FreeboardLikecount findByNickname(String nickname);
	public void deleteByPnumAndNickname(Integer fbnum, String nickname);
}
