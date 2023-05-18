package com.webflux.utils;

import org.springframework.beans.BeanUtils;

// Mapstract

import com.webflux.dto.ProductDto;
import com.webflux.model.Product;

public class AppUtils {

	public static ProductDto entityToDto(Product product) {
		ProductDto productDto = new ProductDto();
		BeanUtils.copyProperties(product, productDto);
		return productDto;
	}

	public static Product dtoToEntity(ProductDto productDto) {
		Product product = new Product();
		BeanUtils.copyProperties(productDto, product);
		return product;
	}

}
