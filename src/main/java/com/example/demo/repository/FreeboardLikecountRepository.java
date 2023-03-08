package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.FreeboardLikecount;

import jakarta.transaction.Transactional;

public interface FreeboardLikecountRepository extends JpaRepository<FreeboardLikecount, Integer> {
	public FreeboardLikecount findByPnumAndNickname(Integer pnum, String nickname);
	@Transactional
	public void deleteByPnumAndNickname(Integer pnum, String nickname);
	public List<FreeboardLikecount> findByPnum(Integer pnum);
}
