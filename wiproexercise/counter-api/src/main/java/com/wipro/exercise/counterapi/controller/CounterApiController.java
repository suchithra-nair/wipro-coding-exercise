package com.wipro.exercise.counterapi.controller;

import static java.util.Map.Entry.comparingByValue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.wipro.exercise.counterapi.bean.CounterApiBean;
import com.wipro.exercise.counterapi.exception.RequestProcessException;
import com.wipro.exercise.counterapi.util.ResponseProcessUtil;

/**
 * @author Suchithra
 *
 */
@RestController
@RequestMapping("/counter-api")
public class CounterApiController {
	
	private static Logger LOGGER = LoggerFactory.getLogger(CounterApiController.class);
	
	@Value(value = "counter.response.csv.file.name")
	private String csvFileName;
	
	//TODO: To be replaced by call to other rest service
	//@Value(value = "counter.response.text.file.name")
	private String textFileName = "sampleText.txt";
	
	@Autowired
	private ResponseProcessUtil reponseProcessUtil;

	
	/**
	 * @param requestBean
	 * @return
	 * @throws IOException
	 * This is originally written as a GetMapping. But the question mentions it as POST which is a bit confusing
	 */
	@PostMapping("/search")
	public ResponseEntity<Map<String,Integer>> search(@RequestBody CounterApiBean requestBean) throws IOException {
		Map<String, Integer> responseMap = null;
		try {
			/*
			 * Path path = Paths.get(textFileName);
			 * 
			 * //Groups the words in the paragraph by number of occurrences Map<String,
			 * Long> wordCount = Files.lines(path) .flatMap(line ->
			 * Arrays.stream(line.trim().split(" "))) .map(word ->
			 * word.replaceAll("[^a-zA-Z]", "").toLowerCase().trim())
			 * .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
			 */
			Map<String, Integer> wordCountMap = loadSearchData();
			responseMap = new HashMap<String, Integer>();
			for (String word : requestBean.getSearchText()) {
				if (wordCountMap.containsKey(word.toLowerCase())) {
					responseMap.put(word, wordCountMap.get(word.toLowerCase()));
				} else {
					responseMap.put(word, 0);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Execption in processing /counter-api/search request : " +e);
			throw new RequestProcessException("Execption in processing /counter-api/search request : " +e.getMessage());
		}

		return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=" + csvFileName)
				.contentType(MediaType.parseMediaType("application/json"))
				.body(responseMap);
		
	}
	
	/**
	 * @param limit
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = "top/{limit}", produces = "text/csv")
	public ResponseEntity<FileSystemResource> getTopText(@PathVariable Long limit) throws IOException{
		Path file = null;
		try {
			
			Map<String, Integer> wordCountMap = loadSearchData();
			if (null != wordCountMap) {
				// Sort the data by the number of occurrences and pick the top {limit} values
				Map<String, Integer> resultMap = wordCountMap.entrySet().stream()
						.sorted(Collections.reverseOrder(comparingByValue())).limit(limit).collect(Collectors
								.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

				// Convert the data into CSV file
				reponseProcessUtil.exportDataToCsv(resultMap);
				file = Paths.get(csvFileName);
			}
		} catch (Exception e) {
			LOGGER.error("Execption in processing /counter-api/top/{limit} request : " +e);
			throw new RequestProcessException("Execption in processing /counter-api/top/{limit} request : " +e.getMessage());
		}
		
		return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=" + csvFileName)
				.contentType(MediaType.parseMediaType("text/csv"))
				.body(new FileSystemResource(file));
		 
	}
	
	private Map<String, Integer> loadSearchData() {
		ResponseEntity<Object> responseEntity = new RestTemplate()
				.getForEntity("http://localhost:8081/search-data/load",  Object.class); //response type
		return (Map<String, Integer>) responseEntity.getBody();
	}
}
