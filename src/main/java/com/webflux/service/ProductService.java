package com.webflux.service;

import com.webflux.dto.ProductDto;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface ProductService {
	
	public Mono<Map<String, String>> addProduct(ProductDto productDto);

	public Flux<ProductDto> getAllProducts();

	public Mono<ProductDto> getProductById(String id);

	public Flux<ProductDto> getProductInRange(double min, double max);

	// To Do properly...
	public Mono<ProductDto> updateProduct(ProductDto productDto, String id);
	
	Mono<ProductDto> updateProductWithoutId(Mono<ProductDto> productDto);

	public Mono<Void> deleteProduct(String id);
}
