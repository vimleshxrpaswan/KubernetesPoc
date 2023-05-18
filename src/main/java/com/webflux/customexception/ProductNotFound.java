package com.webflux.customexception;

public class ProductNotFound extends RuntimeException {

	public ProductNotFound(String string) {
		super(string);
	}

}
