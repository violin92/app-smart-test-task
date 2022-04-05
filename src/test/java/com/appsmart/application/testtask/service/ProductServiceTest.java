package com.appsmart.application.testtask.service;

import com.appsmart.application.testtask.dto.CustomerDto;
import com.appsmart.application.testtask.dto.EditProductDto;
import com.appsmart.application.testtask.dto.ProductDto;
import com.appsmart.application.testtask.mapper.ProductMapper;
import com.appsmart.application.testtask.mapper.ProductMapperImpl;
import com.appsmart.application.testtask.model.CustomerEntity;
import com.appsmart.application.testtask.model.ProductEntity;
import com.appsmart.application.testtask.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    private ProductMapper productMapper = new ProductMapperImpl();

    private ProductService productService;

    @Captor
    private ArgumentCaptor<ProductEntity> productEntityCaptor;

    private final UUID productId = UUID.randomUUID();
    private final UUID customerId = UUID.randomUUID();
    private final Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
    private final List<ProductDto> productDtos = List.of(getProductDto());
    private final List<ProductEntity> productEntities = List.of(getProductEntity());

    @BeforeEach
    void init() {
        productService = new ProductService(productRepository, productMapper);
    }

    @Test
    void getAllProducts() {
        when(productRepository.findAll()).thenReturn(productEntities);
        var result = productService.getAllProducts();
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(productDtos);
    }

    @Test
    void testGetProductById() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(getProductEntity()));
        var result = productService.getProductById(productId);
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(getProductDto());
    }

    @Test
    void testAddProduct() {
        productService.addProduct(getProductDto());
        verify(productRepository).save(productEntityCaptor.capture());
        assertThat(productEntityCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(getProductEntity());
    }

    @Test
    void testUpdateCustomerTitleOnly() {
        var productEntity = new ProductEntity();
        var editProductDto = new EditProductDto();
        productEntity.setId(productId);
        productEntity.setDescription("Old Description");
        productEntity.setPrice(BigDecimal.valueOf(78.00));
        editProductDto.setTitle("New Title");
        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));

        productService.updateProduct(productId, editProductDto);

        verify(productRepository).save(productEntityCaptor.capture());
        assertThat(productEntityCaptor.getValue().getId()).isEqualTo(productId);
        assertThat(productEntityCaptor.getValue().getTitle()).isEqualTo("New Title");
        assertThat(productEntityCaptor.getValue().getDescription()).isEqualTo("Old Description");
        assertThat(productEntityCaptor.getValue().getPrice()).isEqualTo(BigDecimal.valueOf(78.00));
    }

    @Test
    void testUpdateCustomerTitleAndDescription() {
        var productEntity = new ProductEntity();
        var editProductDto = new EditProductDto();
        productEntity.setId(productId);
        productEntity.setDescription("Old Description");
        productEntity.setPrice(BigDecimal.valueOf(78.00));
        editProductDto.setTitle("New Title");
        editProductDto.setDescription("New Description");
        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));

        productService.updateProduct(productId, editProductDto);

        verify(productRepository).save(productEntityCaptor.capture());
        assertThat(productEntityCaptor.getValue().getId()).isEqualTo(productId);
        assertThat(productEntityCaptor.getValue().getTitle()).isEqualTo("New Title");
        assertThat(productEntityCaptor.getValue().getDescription()).isEqualTo("New Description");
        assertThat(productEntityCaptor.getValue().getPrice()).isEqualTo(BigDecimal.valueOf(78.00));
    }

    @Test
    void testUpdateCustomerTitleDescriptionAndPrice() {
        var productEntity = new ProductEntity();
        var editProductDto = new EditProductDto();
        productEntity.setId(productId);
        productEntity.setDescription("Old Description");
        productEntity.setPrice(BigDecimal.valueOf(78.00));
        editProductDto.setTitle("New Title");
        editProductDto.setDescription("New Description");
        editProductDto.setPrice(BigDecimal.valueOf(112.00));
        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));

        productService.updateProduct(productId, editProductDto);

        verify(productRepository).save(productEntityCaptor.capture());
        assertThat(productEntityCaptor.getValue().getId()).isEqualTo(productId);
        assertThat(productEntityCaptor.getValue().getTitle()).isEqualTo("New Title");
        assertThat(productEntityCaptor.getValue().getDescription()).isEqualTo("New Description");
        assertThat(productEntityCaptor.getValue().getPrice()).isEqualTo(BigDecimal.valueOf(112.00));
    }

    @Test
    void testDeleteCustomer() {
        var productEntity = new ProductEntity();
        productEntity.setId(productId);
        when(productRepository.findById(productId)).thenReturn(Optional.of(productEntity));

        productService.deleteProduct(productId);

        verify(productRepository).save(productEntityCaptor.capture());
        assertThat(productEntityCaptor.getValue().getId()).isEqualTo(productId);
        assertThat(productEntityCaptor.getValue().isDeleted()).isEqualTo(true);
    }


    ProductEntity getProductEntity() {
        var product = new ProductEntity();
        var customer = new CustomerEntity();
        customer.setId(customerId);
        product.setId(productId);
        product.setTitle("Product 1");
        product.setDescription("Some Description");
        product.setPrice(BigDecimal.valueOf(78.00));
        product.setCreatedAt(timestamp);
        product.setModifiedAt(timestamp);
        product.setCustomer(customer);
        return product;
    }

    ProductDto getProductDto() {
        var product = new ProductDto();
        var customer = new CustomerDto();
        customer.setId(customerId);
        product.setId(productId);
        product.setTitle("Product 1");
        product.setDescription("Some Description");
        product.setPrice(BigDecimal.valueOf(78.00));
        product.setCreatedAt(timestamp);
        product.setModifiedAt(timestamp);
        product.setCustomer(customer);
        return product;
    }
}
