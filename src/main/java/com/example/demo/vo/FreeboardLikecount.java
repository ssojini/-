package com.example.demo.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;

@Data
@Entity
public class FreeboardLikecount {
	@Id
	@SequenceGenerator(name="FREEBOARD_LIKECOUNT_NUM_GEN",allocationSize=1,sequenceName="FREEBOARD_LIKECOUNT_NUM_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FREEBOARD_LIKECOUNT_NUM_GEN")
	private Integer num;
	private Integer pnum;
	private String nickname;
}
