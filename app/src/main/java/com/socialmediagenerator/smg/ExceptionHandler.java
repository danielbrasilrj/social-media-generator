package com.socialmediagenerator.smg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import groovy.util.logging.Slf4j;

@Slf4j
@ControllerAdvice
public final class ExceptionHandler {

	Logger logger = LoggerFactory.getLogger( ExceptionHandler.class );
	
	@org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleAllExceptions(Exception ex) {
		logger.error("An unexpected error occurred", ex);
		return new ResponseEntity<>("Internal server error. Please contact support.", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
