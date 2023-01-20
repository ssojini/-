package com.example.demo.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.vo.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>
{

}
