package com.webflux.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.webflux.customexception.ProductNotFound;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ProductNotFound.class)
	public ResponseEntity<String> handleCustomerNotFoundException(ProductNotFound cnf) {
		String message = cnf.getMessage();
		return new ResponseEntity<String>(message, HttpStatus.NOT_FOUND);

	}

}
