package com.wipro.exercise.counterapi;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Suchithra
 *
 */
@SpringBootApplication
public class CounterApiApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(CounterApiApplication.class, args);
	}

}
