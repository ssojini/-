package com.example.demo.interfaces;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.AddGoods_Att;

public interface AddGoods_AttReopsitory extends JpaRepository<AddGoods_Att, Integer>
{

	ArrayList<AddGoods_Att> findByGoodsnum(int goodsnum);
	

}
