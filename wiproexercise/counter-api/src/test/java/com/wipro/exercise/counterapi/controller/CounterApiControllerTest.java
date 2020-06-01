package com.wipro.exercise.counterapi.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.wipro.exercise.counterapi.bean.CounterApiBean;

@RunWith(SpringRunner.class)
@WebMvcTest(CounterApiController.class)
public class CounterApiControllerTest {
	
	@Autowired
    private MockMvc mvc;
	
	@Autowired
	private CounterApiController counterApiController;
	
	@Autowired
	private CounterApiBean requestBean;
	
	@Test
	public void testSearch() throws Exception {
		requestBean.setSearchText(Arrays.asList("Duis"));
		
		Map<String, Integer> resultMap = new HashMap<>();
		resultMap.put("Duis", 11);
		ResponseEntity<Map<String, Integer>> responseEntity = ResponseEntity.ok().header("Content-Disposition")
				.contentType(MediaType.parseMediaType("application/json")).body(resultMap);
		
		given(counterApiController.search(requestBean)).willReturn(responseEntity);
		
		mvc.perform(get("http://localhost:8080/counter-api/search")
	               .with(user("admin").password("admin"))
	               .contentType("application/json"))
	               .andExpect(status().isOk());
	}
	

}
