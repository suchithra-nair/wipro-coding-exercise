package com.wipro.exercise.counterapi.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ResponseProcessUtil {
	private static Logger LOGGER = LoggerFactory.getLogger(ResponseProcessUtil.class);

	@Value("${counter.response.csv.file.name}")
	private String csvFileName;

	public void exportDataToCsv(Map<String, Integer> resultMap) {

		try (	BufferedWriter writer = Files.newBufferedWriter(Paths.get(csvFileName));
				CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withDelimiter('|'));
			) {

			for (Entry<String, Integer> entry : resultMap.entrySet()) {
				csvPrinter.printRecord(Arrays.asList(entry.getKey(), entry.getValue()));
			}
			csvPrinter.flush();
		} catch (IOException exception) {
			LOGGER.error("Error occurred in processing CSV file " + exception);
		}
	}

}
