package com.appsmart.application.testtask.controller;

import com.appsmart.application.testtask.dto.EditProductDto;
import com.appsmart.application.testtask.dto.ProductDto;
import com.appsmart.application.testtask.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @Mock
    private List<ProductDto> products;

    @Mock
    private ProductDto product;

    @Mock
    private EditProductDto editProduct;

    @Captor
    private ArgumentCaptor<ProductDto> productDtoCaptor;

    private ProductController productController;

    private final UUID id = UUID.randomUUID();

    @BeforeEach
    void init() {
        productController = new ProductController(productService);
    }

    @Test
    void testGetAllCustomers() {
        when(productService.getAllProducts()).thenReturn(products);
        var result = productController.getAllProducts();
        assertEquals(products, result);
    }

    @Test
    void testGetCustomerById() {
        when(productService.getProductById(id)).thenReturn(product);
        var result = productController.getProductById(id);
        assertEquals(product, result);
    }

    @Test
    void testAddCustomer() {
        productController.addProduct(product);
        verify(productService).addProduct(productDtoCaptor.capture());
        assertEquals(product, productDtoCaptor.getValue());
    }

    @Test
    void testUpdateCustomer() {
        var editProductCaptor = ArgumentCaptor.forClass(EditProductDto.class);
        productController.updateProduct(id, editProduct);
        verify(productService).updateProduct(eq(id), editProductCaptor.capture());
        assertEquals(editProduct, editProductCaptor.getValue());
    }

    @Test
    void testDeleteCustomer() {
        productController.deleteProduct(id);
        verify(productService).deleteProduct(id);
    }
}
