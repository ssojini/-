package com.example.demo.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Authorities {
	@Id
	private String userid;
	private String authority;
}
