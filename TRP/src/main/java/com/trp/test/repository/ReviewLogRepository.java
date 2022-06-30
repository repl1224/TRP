package com.trp.test.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trp.test.model.entity.Review;
import com.trp.test.model.entity.ReviewLog;
import com.trp.test.model.entity.ReviewLogKey;

public interface ReviewLogRepository extends JpaRepository<ReviewLog, ReviewLogKey> {

    @Query(value = "SELECT type, action, review_id as reviewId, user_id as userId, place_id as placeId, content, attached_photo_ids as attachedPhotoIds FROM review WHERE review_id = :reviewId", nativeQuery = true)
    List<Review> getReviewByReviewId(@Param("reviewId") String reviewId);
}
