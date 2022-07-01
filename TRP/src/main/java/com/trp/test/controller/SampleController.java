package com.trp.test.controller;

import java.nio.charset.Charset;

import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trp.test.service.ReviewService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Sample")
@RestController
//@RequestMapping("/test")
public class SampleController {
    
    @Autowired
    private ReviewService reviewService;
    
    /**
     * 리뷰를 추가한다.
     * @param reviewInfo 리뷰 정보 JSONObject
     * @throws ParseException
     */
    @ApiOperation(value = "events", tags = "리뷰 이벤트")
    @PostMapping(value = "/events")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> editReview(@RequestParam("reviewInfo") String param) throws ParseException {
    	int result = reviewService.editReview(param);
    	
    	HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	 if(result == HttpStatus.CONFLICT.value()) {
    		
    		return new ResponseEntity<>("등록된 리뷰가 존재합니다.", headers, HttpStatus.CONFLICT);
    	} else if(result == HttpStatus.NOT_FOUND.value()) {
    		
    		return new ResponseEntity<>("등록된 리뷰가 없습니다.", headers, HttpStatus.NOT_FOUND);
    	} else if(result == HttpStatus.BAD_REQUEST.value()) {
    		
    		return new ResponseEntity<>("잘못된 요청입니다.", headers, HttpStatus.BAD_REQUEST);
    	} else {
    		
    		return new ResponseEntity<>("리뷰가 등록되었습니다.", headers, HttpStatus.CREATED);
    	}
    }
    
    /**
     * 사용자별 리뷰 보상점수 합을 구한다
     * @param reviewInfo 리뷰 정보 JSONObject
     * @throws ParseException
     */
    @ApiOperation(value = "getScoreSum", tags = "사용자별 리뷰 보상점수 합")
    @GetMapping(value = "/getScoreSum")
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<?> getScoreSum(@RequestParam("userInfo") String param) throws ParseException {
    	Long score = reviewService.getScoreSum(param);
    	
    	HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
		return new ResponseEntity<>(score, headers, HttpStatus.OK);
    }
    
    
}
