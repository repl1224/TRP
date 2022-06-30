package com.trp.test.model.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.IdClass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@IdClass(ReviewLogKey.class)
public class ReviewLogKey implements Serializable {
	private String type;
	
	private String action;

    private String reviewId;
    
    private String userId;
	
    private String placeId;
	
	private String content;
	
	private String attachedPhotoIds;
	
	private Timestamp crtDthr;
}
