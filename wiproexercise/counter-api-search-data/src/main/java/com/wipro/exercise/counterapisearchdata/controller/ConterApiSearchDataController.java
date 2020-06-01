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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Suchithra
 *
 */
@RestController
@RequestMapping("/search-data")
public class ConterApiSearchDataController {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ConterApiSearchDataController.class);
	
	@Value("${counter.response.text.file.name}")
	private String textFileName;
		
	/**Reads the sample data from file and groups the words in the paragraph by number of occurrences
	 * @return
	 */
	@GetMapping("/load")
	public Map<String, Long> loadSearchData() {
		Path path = Paths.get(textFileName);
		Map<String, Long> wordCountMap = null;
		
		try {
			wordCountMap = Files.lines(path)
					.flatMap(line -> Arrays.stream(line.trim().split(" ")))
					.map(word -> word.replaceAll("[^a-zA-Z]", "").toLowerCase().trim())
					.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		} catch (IOException e) {
			LOGGER.error("Error occurred in processing input file " + e);
		}
		
		return wordCountMap;
	}

}
