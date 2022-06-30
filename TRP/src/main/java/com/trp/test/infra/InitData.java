package com.trp.test.infra;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.trp.test.model.entity.Review;
import com.trp.test.model.entity.ReviewKey;
import com.trp.test.model.entity.ReviewLog;
import com.trp.test.model.entity.ReviewLogKey;
import com.trp.test.model.entity.ReviewScore;
import com.trp.test.repository.ReviewLogRepository;
import com.trp.test.repository.ReviewRepository;
import com.trp.test.repository.ReviewScoreRepository;

@Component
public class InitData {
    
    @Autowired
    ReviewRepository reviewRepository;
    
    @Autowired
    ReviewScoreRepository reviewScoreRepository;
    
    @Autowired
    ReviewLogRepository reviewLogRepository;
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostConstruct
    private void initReview() throws IOException {
    	
        if (reviewRepository.count() == 0) {
            Resource resource1 = new ClassPathResource("리뷰.txt");
            List<Review> reviewList = Files.readAllLines(resource1.getFile().toPath(), StandardCharsets.UTF_8)
                    .stream()
                    .skip(1)
                    .map(line -> {
                        String[] split = line.split("\t");
                        
                        ReviewKey reviewKey = ReviewKey.builder()
                        	.reviewId(split[0].replaceAll("[\\n\\t ]", "").trim())
                        	.userId(split[1].replaceAll("[\\n\\t ]", "").trim())
                        	.placeId(split[2].replaceAll("[\\n\\t ]", "").trim())
                        	.build();
                        
                        return Review.builder()
                        		.reviewId(split[0].replaceAll("[\\n\\t ]", "").trim())
                        		.userId(split[1].replaceAll("[\\n\\t ]", "").trim())
                        		.placeId(split[2].replaceAll("[\\n\\t ]", "").trim())
//                        		.reviewKey(reviewKey)
                        		.content(split[3].replaceAll("[\\n\\t ]", "").trim())
                        		.attachedPhotoIds(split[4].replaceAll("[\\n\\t ]", "").trim())
                                .build();
                    }).collect(Collectors.toList());
            reviewRepository.saveAll(reviewList);
        }
        
        if (reviewScoreRepository.count() == 0) {
            Resource resource2 = new ClassPathResource("리뷰점수.csv");
            List<ReviewScore> reviewScoreList = Files.readAllLines(resource2.getFile().toPath(), StandardCharsets.UTF_8)
                    .stream().skip(1).map(line -> {
                        String[] split = line.split(",");
                        return ReviewScore.builder()
                        		.userId(split[0])
                        		.reviewId(split[1])
                        		.score(Long.parseLong(split[2]))
                                .build();
                    }).collect(Collectors.toList());
            reviewScoreRepository.saveAll(reviewScoreList);
        }
        
        if (reviewLogRepository.count() == 0) {
            Resource resource3 = new ClassPathResource("리뷰로그.txt");
            List<ReviewLog> reviewLogList = Files.readAllLines(resource3.getFile().toPath(), StandardCharsets.UTF_8)
                    .stream().skip(1).map(line -> {
                        String[] split = line.split("\t");
                        
                        String strDate = split[7].replaceAll("[\\n\\t ]", " ").trim();
                        
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
          		        Date date;
          		        Timestamp timeStampDate = null;
						try {
							date = dateFormat.parse(strDate);
							timeStampDate = new Timestamp(date.getTime());
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                        
                        ReviewLogKey reviewLogKey = ReviewLogKey.builder()
                		.type(split[0].replaceAll("[\\n\\t ]", "").trim())
                		.action(split[1].replaceAll("[\\n\\t ]", "").trim())
                		.reviewId(split[2].replaceAll("[\\n\\t ]", "").trim())
                		.userId(split[3].replaceAll("[\\n\\t ]", "").trim())
                		.placeId(split[4].replaceAll("[\\n\\t ]", "").trim())
                		.content(split[5].replaceAll("[\\n\\t ]", "").trim())
                		.attachedPhotoIds(split[6].replaceAll("[\\n\\t ]", "").trim())
                		.crtDthr(timeStampDate)
                        .build();
                        
                        return ReviewLog.builder()
                        		.reviewLogKey(reviewLogKey)
                                .build();
                    }).collect(Collectors.toList());
            reviewLogRepository.saveAll(reviewLogList);
        }
    }
}
