package com.wipro.exercise.counterapi.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.wipro.exercise.counterapi.util.ResponseProcessUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CounterApiControllerTest {
	
	private String searchApiEndPoint;
	private String getTopTextApiEndPoint;
	private String jsonInputSearch;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private ResponseProcessUtil responseProcessUtil;
	
	@InjectMocks
	private CounterApiController counterApiController;

	@Before
	public void setUp() throws Exception {
		searchApiEndPoint = "http://localhost:8080/counter-api/search";
		jsonInputSearch = "{\"searchText\":[\"Duis\", \"Sed\", \"Donec\", \"Augue\", \"Pellentesque\", \"123\"]}";
		getTopTextApiEndPoint = "http://localhost:8080/counter-api/top/{5}";
	}

	@Test
	public void testSearch() throws Exception {
		MvcResult response = mockMvc
				.perform(MockMvcRequestBuilders.post(searchApiEndPoint)
				.contentType(MediaType.APPLICATION_JSON).content(jsonInputSearch))
				.andExpect(status().isOk())
				.andReturn();
			assertNotNull(response);
	}

	@Test
	public void testGetTopText() throws Exception {
		Integer uriVars= 5;
		MvcResult response = mockMvc
				.perform(MockMvcRequestBuilders.get(getTopTextApiEndPoint, uriVars))
				.andExpect(status().isOk())
				.andReturn();
			assertNotNull(response);
			assertEquals(MediaType.parseMediaType(response.getResponse().getContentType().toString()), 
					MediaType.parseMediaType("text/csv"));
	}
}
