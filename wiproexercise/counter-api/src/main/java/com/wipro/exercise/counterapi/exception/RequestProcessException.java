package com.wipro.exercise.counterapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class RequestProcessException extends RuntimeException {

	private static final long serialVersionUID = -4452179241707632611L;

	public RequestProcessException(String arg0) {
		super(arg0);
	}

}
