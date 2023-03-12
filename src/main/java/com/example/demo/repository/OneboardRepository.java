package com.example.demo.repository;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.vo.OneBoard;

public interface OneboardRepository extends JpaRepository<OneBoard, Integer>{
	public Page<OneBoard> findAll(Pageable pageable);
	public Page<OneBoard> findAllByOrderByQdateDesc(Pageable pageable);
	
	//이용자별 1:1 문의 리스트 가져오기
	@Query(value = "SELECT qnum, LPAD('　', (LEVEL-1)*3, '　') || title title, author, qdate, content, hit, anum FROM queboard START WITH author = ?1 CONNECT BY PRIOR qnum = anum ORDER SIBLINGS BY qnum DESC", nativeQuery = true)
    Page<OneBoard> getQnaByAuthor(Pageable pageable, String author);
	
	//관리자용 1:1문의 리스트 전체 가져오기
    @Query(value = "SELECT qnum, LPAD('　', (LEVEL-1)*3, '　') || title title, author, qdate, content, hit, anum "
            + "FROM queBoard "
            + "START WITH anum = 0 "
            + "CONNECT BY PRIOR qnum = anum "
            + "ORDER SIBLINGS BY qnum DESC", nativeQuery = true)
   Page<OneBoard> findQAList(Pageable pageable);

}
