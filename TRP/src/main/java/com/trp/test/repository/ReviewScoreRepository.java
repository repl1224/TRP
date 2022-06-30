package com.trp.test.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trp.test.model.entity.ReviewScore;

public interface ReviewScoreRepository extends JpaRepository<ReviewScore, String> {

    @Query(value = "SELECT user_id as userId, review_id as reviewId, score FROM review_score WHERE user_id = :userId", nativeQuery = true)
    List<ReviewScore> getReviewScoreByUserId(@Param("userId") String userId);

    Optional<List<ReviewScore>> findByUserId(@Param("userId") String userId);
    
    Optional<ReviewScore> findByUserIdAndReviewId(@Param("userId") String userId, @Param("reviewId") String reviewId);
    
    void deleteByUserIdAndReviewId(@Param("userId") String userId, @Param("reviewId") String reviewId);
}
