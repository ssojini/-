package com.example.demo.interfaces;

import java.util.ArrayList;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.vo.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer>
{

	ArrayList<Cart> findByUserid(String userid);

	Cart findByCartnumAndUserid(int cartnum, String userid);

}
