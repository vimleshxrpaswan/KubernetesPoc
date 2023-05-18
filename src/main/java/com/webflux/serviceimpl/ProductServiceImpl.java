package com.webflux.serviceimpl;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.webflux.customexception.ProductNotFound;
import com.webflux.dto.ProductDto;
import com.webflux.repository.ProductRepository;
import com.webflux.service.ProductService;
import com.webflux.utils.AppUtils;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@Slf4j
@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductRepository productRepository;
	
	public Mono<Map<String, String>> addProduct(ProductDto productDto) {
		log.info("ProductInfo  "+ productDto );
		return Mono.just(productDto)
				.map(AppUtils::dtoToEntity)
				.flatMap(productRepository::insert)
				.then(Mono.just(Collections.singletonMap("message","saved successfully")));
//				.map(AppUtils::entityToDto);
//		return productRepository.save(product);
	}

	public Flux<ProductDto> getAllProducts() {
		return productRepository.findAll()
				.map(AppUtils::entityToDto);
	}

	public Mono<ProductDto> getProductById(String id) {
		return productRepository
				.findById(id)
				.map(AppUtils::entityToDto)
				.switchIfEmpty(Mono.error(new ProductNotFound("Product with " +id +" not found")));
	}

	public Flux<ProductDto> getProductInRange(double min, double max) {
		return productRepository.findByPriceBetween(Range.closed(min, max))
				.switchIfEmpty(Mono.error(new ProductNotFound("Product in range "+min+" and " + max+" not found")));
		

	}

	// To Do properly...
	@SuppressWarnings("deprecation")
	public Mono<ProductDto> updateProduct(ProductDto productDto, String id) {
		return productRepository.findById(id)
				.switchIfEmpty(Mono.error(new ProductNotFound("Product with "+ id +" not found")))
				.flatMap(p -> {
					p.setId(id);
					p.setName(!StringUtils.isEmpty(productDto.getName()) ? productDto.getName() : p.getName());
					p.setPrice(Objects.nonNull(productDto.getPrice()) ? productDto.getPrice() : p.getPrice());
					p.setQuantity(Objects.nonNull(productDto.getQuantity()) ? productDto.getQuantity() : p.getQuantity());
					return productRepository.save(p)
					.then(Mono.just(AppUtils.entityToDto(p)));		
				});
				
	}
	
	@Override
	public Mono<ProductDto> updateProductWithoutId(Mono<ProductDto> productDto) {
		return productDto
				.map(AppUtils::dtoToEntity)
				.flatMap(productRepository::save)
				.map(AppUtils::entityToDto);
				
	}

	public Mono<Void> deleteProduct(String id) {
		return productRepository.deleteById(id)
				.switchIfEmpty(Mono.error(new ProductNotFound("Product with "+ id +" not found")));

	}
 
}
