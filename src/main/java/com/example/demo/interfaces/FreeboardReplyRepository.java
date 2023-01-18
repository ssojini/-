package com.example.demo.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.FreeboardReply;

public interface FreeboardReplyRepository extends JpaRepository<FreeboardReply,Integer> {

	List<FreeboardReply> findAllByPnumOrderByDatetimeDesc(Integer pnum);
}
