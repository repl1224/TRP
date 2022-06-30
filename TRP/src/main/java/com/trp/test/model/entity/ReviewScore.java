package com.trp.test.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(ReviewScoreKey.class)
public class ReviewScore implements Serializable {
	@Id
    private String userId;
	
	@Id
	private String reviewId;
	
	private Long score;

}
