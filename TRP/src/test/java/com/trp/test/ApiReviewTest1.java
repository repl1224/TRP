package com.trp.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ApiReviewTest1 {
	
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void apiTest() throws Exception {
		String param = "{type:\"REVIEW\",action:\"ADD\",reviewId:\"240a0658-dc5f-4878-9381-ebb7b2667773\",content:\"좋아요!\",attachedPhotoIds:[\"e4d1a64e-a531-46de-88d0-ff0ed70c0bb8\", \"afb0cef2- 851d-4a50-bb07-9cc15cbdc332\"],userId:\"3ede0ef2-92b7-4817-a5f3-0c575361f746\",placeId:\"2e4baf1c-5acb-4efb-a1af-eddada31b00g\"}";
		
		mockMvc.perform(post("/events").param("reviewInfo", param))
			.andExpect(status().isOk())
			.andDo(print());
		
	}

}
