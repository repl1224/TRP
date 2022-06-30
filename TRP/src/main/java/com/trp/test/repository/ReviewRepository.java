package com.trp.test.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trp.test.model.entity.Review;
import com.trp.test.model.entity.ReviewKey;

public interface ReviewRepository extends JpaRepository<Review, ReviewKey> {

    @Query(value = "SELECT review_id as reviewId, user_id as userId, place_id as placeId, content, attached_photo_ids as attachedPhotoIds FROM review WHERE review_id = :reviewId", nativeQuery = true)
    List<Review> getReviewByReviewId(@Param("reviewId") String reviewId);

//    List<Review> findByreviewId(@Param("reviewId") String reviewId);
    
    int countByPlaceId(@Param("placeId") String placeId);
    
//    Optional<Review> findByUserIdAndPlaceId(@Param("userId") String userId, @Param("placeId") String placeId);
//    
//    Optional<Review> findByReviewIdAndUserIdAndPlaceId(@Param("reviewId") String reviewId, @Param("userId") String userId, @Param("placeId") String placeId);
}
