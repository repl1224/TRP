package com.trp.test.model;

public interface ReviewLogResult {
	String getType();
	String getAction();
	String getReviewId();
    String getUserId();
    String getPlaceId();
    String getContent();
    String getAttachedPhotoIds();
}
