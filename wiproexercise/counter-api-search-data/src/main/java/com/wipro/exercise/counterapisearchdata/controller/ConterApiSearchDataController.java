package com.wipro.exercise.counterapisearchdata.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search-data")
public class ConterApiSearchDataController {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ConterApiSearchDataController.class);
	
	private String textFileName = "sampleText.txt";
		
	@GetMapping("/load")
	public Map<String, Long> loadSearchData() {
		Path path = Paths.get(textFileName);
		Map<String, Long> wordCountMap = null;
		
		//Groups the words in the paragraph by number of occurrences
		try {
			wordCountMap = Files.lines(path)
					.flatMap(line -> Arrays.stream(line.trim().split(" ")))
					.map(word -> word.replaceAll("[^a-zA-Z]", "").toLowerCase().trim())
					.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		} catch (IOException e) {
			LOGGER.error("Error occurred in processing CSV file " + e);
		}
		
		return wordCountMap;
	}

}
