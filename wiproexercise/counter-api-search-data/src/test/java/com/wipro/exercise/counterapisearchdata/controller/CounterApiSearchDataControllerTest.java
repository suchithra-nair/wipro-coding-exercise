package com.wipro.exercise.counterapisearchdata.controller;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class CounterApiSearchDataControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	private String apiEndPoint ;

	@InjectMocks
	private CounterApiSearchDataController counterApiSearchDataController;
	
	@BeforeEach
	void setUp() throws Exception {
		apiEndPoint = "http://localhost:8081/search-data/load";
	}

	@Test
	void testLoadSearchData() throws Exception {
		MvcResult response = mockMvc
			.perform(MockMvcRequestBuilders.get(apiEndPoint))
			.andExpect(status().isOk())
			.andReturn();
		assertNotNull(response);
	}

}
