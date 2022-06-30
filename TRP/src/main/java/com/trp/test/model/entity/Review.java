package com.trp.test.model.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EmbeddedId;
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
@IdClass(ReviewKey.class)
public class Review implements Serializable {
	@Id
    private String reviewId;
    
	@Id
    private String userId;
	
	@Id
    private String placeId;
//	@EmbeddedId
//	private ReviewKey reviewKey;
	
	private String content;
	
	private String attachedPhotoIds;

}
