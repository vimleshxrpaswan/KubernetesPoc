package com.webflux.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.webflux.dto.ProductDto;
import com.webflux.service.ProductService;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductService productServcie;

	@PostMapping("/addProduct")
	public Mono<Map<String, String>> saveProduct(@RequestBody ProductDto productDto){
		return productServcie.addProduct(productDto);
//				.then(Mono.just("product uploaded successfully..."))
//				.onErrorResume(e -> Mono.just("please provide product body..."));
	}
	
	@GetMapping("/getAllProducts")
	public Flux<ProductDto> getAllProducts(){
		return productServcie.getAllProducts();
				
	}
	
	@GetMapping("/getProduct/{id}")
	public Mono<ProductDto> getProductById(@PathVariable String id){
		return productServcie.getProductById(id);
	}
	
	@GetMapping("/getProductrange")
	public Flux<ProductDto> getProductInPriceRange(@RequestParam double min, @RequestParam double max){
		return productServcie.getProductInRange(min, max);
	}
	
	
	@PutMapping("/updateProduct/{id}")
	public Mono<ProductDto> updateProduct(@RequestBody ProductDto productDto,@PathVariable String id){
		return productServcie.updateProduct(productDto, id);
	}
	
	@PutMapping("/updateProductWithoutId")
	public Mono<ProductDto> updateProductWithoutId(@RequestBody Mono<ProductDto> productDto){
		return productServcie.updateProductWithoutId(productDto);
	}
	
	@DeleteMapping("/deleteProduct/{id}")
	public Disposable deleteProduct(@PathVariable String id){
		return productServcie.deleteProduct(id).subscribe();
	}
	
	
	
}
