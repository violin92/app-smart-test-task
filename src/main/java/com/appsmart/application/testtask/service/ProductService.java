package com.appsmart.application.testtask.service;

import com.appsmart.application.testtask.dto.EditProductDto;
import com.appsmart.application.testtask.dto.ProductDto;
import com.appsmart.application.testtask.mapper.ProductMapper;
import com.appsmart.application.testtask.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public List<ProductDto> getAllProducts() {
        return productRepository.findAll().stream()
                .filter(productEntity -> !productEntity.isDeleted())
                .map(productMapper::toDto)
                .collect(Collectors.toList());
    }

    public ProductDto getProductById(UUID id) {
        var productOptional = productRepository.findById(id);
        if (productOptional.isPresent() && !productOptional.get().isDeleted()) {
            return productMapper.toDto(productOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void updateProduct(UUID id, EditProductDto productDto) {
        var productOptional = productRepository.findById(id);
        if (productOptional.isPresent() && !productOptional.get().isDeleted()) {
            if (productDto.getTitle() != null) {
                productOptional.get().setTitle(productDto.getTitle());
            }
            if (productDto.getDescription() != null) {
                productOptional.get().setDescription(productDto.getDescription());
            }
            if (productDto.getPrice() != null) {
                productOptional.get().setPrice(productDto.getPrice());
            }
            productRepository.save(productOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void addProduct(ProductDto productDto) {
        var productEntity = productMapper.toEntity(productDto);
        productRepository.save(productEntity);
    }

    public void deleteProduct(UUID id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        product.setDeleted(true);
        productRepository.save(product);
    }
}
