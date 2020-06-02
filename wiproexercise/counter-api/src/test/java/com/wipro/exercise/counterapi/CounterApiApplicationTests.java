package com.wipro.exercise.counterapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.wipro.exercise.counterapi.controller.CounterApiControllerTest;

@SpringBootTest(classes = CounterApiControllerTest.class)
class CounterApiApplicationTests {
	
	@Test
	void contextLoads() {
	}

}
