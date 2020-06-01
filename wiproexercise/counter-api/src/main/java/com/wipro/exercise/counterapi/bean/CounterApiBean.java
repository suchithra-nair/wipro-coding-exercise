package com.wipro.exercise.counterapi.bean;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModel;

/**
 * @author Suchithra
 * Bean was initially designed to hold the request/response data but was later not needed.
 * Can be removed
 *
 */
@ApiModel("Counter API details")
public class CounterApiBean {
	private List<String> searchText;
	private Map<String, Long> counts;

	public List<String> getSearchText() {
		return searchText;
	}

	public void setSearchText(List<String> searchText) {
		this.searchText = searchText;
	}

	public Map<String, Long> getCounts() {
		return counts;
	}

	public void setCounts(Map<String, Long> counts) {
		this.counts = counts;
	}

}
