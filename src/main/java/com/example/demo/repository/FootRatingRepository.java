package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.vo.FoodRating;

public interface FootRatingRepository extends JpaRepository<FoodRating, String> {
}
