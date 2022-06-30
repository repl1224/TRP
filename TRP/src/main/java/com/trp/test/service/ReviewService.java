package com.trp.test.service;

import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trp.test.model.entity.Review;
import com.trp.test.model.entity.ReviewKey;
import com.trp.test.model.entity.ReviewLog;
import com.trp.test.model.entity.ReviewLogKey;
import com.trp.test.model.entity.ReviewScore;
import com.trp.test.repository.ReviewLogRepository;
import com.trp.test.repository.ReviewRepository;
import com.trp.test.repository.ReviewScoreRepository;

@Service
@Transactional
public class ReviewService {

	@Autowired
	ReviewRepository reviewRepository;
    
    @Autowired
    ReviewScoreRepository reviewScoreRepository;
    
    @Autowired
    ReviewLogRepository reviewLogRepository;
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    /**
     * 리뷰를 추가한다.
     * @param reviewInfo 사용자 정보 JSONObject 문자열
     * @throws ParseException
     */
    public int editReview(String reviewInfo) throws ParseException {
    	JSONParser jp = new JSONParser(reviewInfo);
    	
    	LinkedHashMap map = jp.parseObject();
    	
    	String strType = (String) map.get("type");
    	String strAction = (String) map.get("action");
    	String strReviewId = (String) map.get("reviewId");
    	String strUserId = (String) map.get("userId");
    	String strPlaceId = (String) map.get("placeId");
    	String strContent = (String) map.get("content");
    	List<String> photoList = (List) map.get("attachedPhotoIds");
    	
    	String strAttachedPhotoIds = "";
    	if(photoList != null && photoList.size() > 0) {
    		for(String strPhoto : photoList) {
    			strAttachedPhotoIds = strAttachedPhotoIds + "\"" + strPhoto + "\",";
        	}
    	}
    	
    	if(!"".equals(strAttachedPhotoIds)) {
    		strAttachedPhotoIds = strAttachedPhotoIds.substring(0, strAttachedPhotoIds.length() - 1);
    		strAttachedPhotoIds = "[" + strAttachedPhotoIds + "]";
    	}
    	
    	int result = 0;
    	
    	if("REVIEW".equals(strType)) {
    		Long datetime = System.currentTimeMillis();
            Timestamp timestamp = new Timestamp(datetime);
        	
        	ReviewLogKey reviewLogKey = ReviewLogKey.builder()
        			.type(strType)
        			.action(strAction)
        			.reviewId(strReviewId)
        			.userId(strUserId)
        			.placeId(strPlaceId)
        			.content(strContent)
        			.attachedPhotoIds(strAttachedPhotoIds)
        			.crtDthr(timestamp)
        			.build();
        	
        	ReviewLog reviewLog = ReviewLog.builder()
        			.reviewLogKey(reviewLogKey)
        			.build();
        	
        	reviewLogRepository.save(reviewLog);
        	
        	ReviewKey reviewKey = ReviewKey.builder()
            	.reviewId(strReviewId)
            	.userId(strUserId)
            	.placeId(strPlaceId)
            	.build();
        	
        	Review review = Review.builder()
        		.reviewId(strReviewId)
    			.userId(strUserId)
    			.placeId(strPlaceId)
//        		.reviewKey(reviewKey)
    			.content(strContent)
    			.attachedPhotoIds(strAttachedPhotoIds).build();
        	
        	if(strAction != null && "ADD".equals(strAction)) {
        		Optional<Review> reviewOpt = reviewRepository.findById(reviewKey);
        		if(reviewOpt.isPresent()) {
            		return HttpStatus.CONFLICT.value();
            	}
        		
        		
        		int existCnt = reviewRepository.countByPlaceId(strPlaceId);
        		
        		reviewRepository.save(review);
        		
        		Long curScore = 0L;
        		
        		if(strContent != null && strContent.length() > 0) {
    				curScore += 1;
    			}
    			
    			if(photoList != null && photoList.size() > 0) {
    				curScore += 1;
    			}
    			
    			if(existCnt == 0) {
    				curScore += 1;
    			}
    			
    			ReviewScore reviewScore = ReviewScore.builder()
    					.userId(strUserId)
    					.reviewId(strReviewId)
    					.score(curScore).build();
    			
    			reviewScoreRepository.save(reviewScore);
    			
    			result = HttpStatus.CREATED.value();
        		
        	} else if(strAction != null && "MOD".equals(strAction)) {
        		Long curScore = 0L;
        		Optional<ReviewScore> opt = reviewScoreRepository.findByUserIdAndReviewId(strUserId, strReviewId);
        		if(opt.isPresent()) {
        			curScore = opt.get().getScore();
        		}
        		
        		Optional<Review> reviewOpt = reviewRepository.findById(reviewKey);
        		
        		if(!reviewOpt.isPresent()) {
        			return HttpStatus.NOT_FOUND.value();
        		}
        		
        		int curPhotosCnt = 0;
        		String curPhotos = reviewOpt.get().getAttachedPhotoIds();
        		
        		if(curPhotos != null && !"".equals(curPhotos)) {
        			JSONParser jpp = new JSONParser(curPhotos);
        			List vaPhoto = (List) jpp.parse();
        			
        			curPhotosCnt = vaPhoto.size();
        		}
        		
        		reviewRepository.save(review);
        		
    			if(curPhotosCnt == 0 && (photoList != null && photoList.size() > 0)) {
    				curScore += 1;
    			}
    			
    			if(curPhotosCnt != 0 && (photoList == null || photoList.size() == 0)) {
    				curScore -= 1;
    			}
    			
    			ReviewScore reviewScore = ReviewScore.builder()
    					.userId(strUserId)
    					.reviewId(strReviewId)
    					.score(curScore).build();
        		
    			reviewScoreRepository.save(reviewScore);
    			
    			result = HttpStatus.CREATED.value();
        		
        	} else if(strAction != null && "DELETE".equals(strAction)) {
        		Optional<Review> reviewOpt = reviewRepository.findById(reviewKey);
        		
        		reviewRepository.delete(review);
        		
        		reviewScoreRepository.deleteByUserIdAndReviewId(strUserId, strReviewId);
        		
        		result = HttpStatus.CREATED.value();
        	} else {
        		return HttpStatus.BAD_REQUEST.value();
        	}
        	
        	return result;
    	} else {
    		return HttpStatus.BAD_REQUEST.value();
    	}
    	
    }
    
    public Long getScoreSum(String userInfo) throws ParseException {
    	JSONParser jp = new JSONParser(userInfo);
    	
    	LinkedHashMap map = jp.parseObject();
    	
    	String strUserId = (String) map.get("userId");
    	
    	Optional<List<ReviewScore>> opt = reviewScoreRepository.findByUserId(strUserId);
    	
    	if(opt.isPresent()) {
    		return opt.get().stream()
    				.mapToLong(ReviewScore::getScore)
    				.sum();
    	} else {
    		return 0L;
    	}
    }
    
}
