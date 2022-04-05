package com.appsmart.application.testtask.controller;

import com.appsmart.application.testtask.dto.EditProductDto;
import com.appsmart.application.testtask.dto.ProductDto;
import com.appsmart.application.testtask.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    ProductDto getProductById(@PathVariable UUID id) {
        return productService.getProductById(id);
    }

    @PostMapping
    void addProduct(@RequestBody ProductDto productDto) {
        productService.addProduct(productDto);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    void updateProduct(@PathVariable UUID id, @RequestBody EditProductDto productDto) {
        productService.updateProduct(id, productDto);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    void deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
    }
}
