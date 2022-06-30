package com.trp.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiReviewTest2 {
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void apiTest() throws Exception {
		
		String param = "{userId:\"3ede0ef2-92b7-4817-a5f3-0c575361f745\"}";
		
		mockMvc.perform(get("/getScoreSum").param("userInfo", param))
			.andExpect(status().isOk())
			.andDo(print());
		
	}

}
