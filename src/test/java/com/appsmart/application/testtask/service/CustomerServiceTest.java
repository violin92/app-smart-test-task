package com.appsmart.application.testtask.service;

import com.appsmart.application.testtask.dto.CustomerDto;
import com.appsmart.application.testtask.dto.EditCustomerDto;
import com.appsmart.application.testtask.mapper.CustomerMapper;
import com.appsmart.application.testtask.mapper.CustomerMapperImpl;
import com.appsmart.application.testtask.mapper.ProductMapper;
import com.appsmart.application.testtask.mapper.ProductMapperImpl;
import com.appsmart.application.testtask.model.CustomerEntity;
import com.appsmart.application.testtask.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    private ProductMapper productMapper = new ProductMapperImpl();

    private CustomerMapper customerMapper = new CustomerMapperImpl(productMapper);

    private CustomerService customerService;

    @Captor
    private ArgumentCaptor<CustomerEntity> customerEntityCaptor;

    private final UUID id = UUID.randomUUID();
    private final Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
    private final List<CustomerDto> customerDtos = List.of(getCustomerDto());
    private final List<CustomerEntity> customerEntities = List.of(getCustomerEntity());

    @BeforeEach
    void init() {
        customerService = new CustomerService(customerRepository, customerMapper);
    }

    @Test
    void getAllCustomers() {
        when(customerRepository.findAll()).thenReturn(customerEntities);
        var result = customerService.getAllCustomers();
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(customerDtos);
    }

    @Test
    void testGetCustomerById() {
        when(customerRepository.findById(id)).thenReturn(Optional.of(getCustomerEntity()));
        var result = customerService.getCustomerById(id);
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(getCustomerDto());
    }

    @Test
    void testAddCustomer() {
        customerService.addCustomer(getCustomerDto());
        verify(customerRepository).save(customerEntityCaptor.capture());
        assertThat(customerEntityCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(getCustomerEntity());
    }

    @Test
    void testUpdateCustomer() {
        var customerEntity = new CustomerEntity();
        var editCustomerDto = new EditCustomerDto();
        customerEntity.setId(id);
        editCustomerDto.setTitle("New Title");
        when(customerRepository.findById(id)).thenReturn(Optional.of(customerEntity));

        customerService.updateCustomer(id, editCustomerDto);

        verify(customerRepository).save(customerEntityCaptor.capture());
        assertThat(customerEntityCaptor.getValue().getId()).isEqualTo(id);
        assertThat(customerEntityCaptor.getValue().getTitle()).isEqualTo("New Title");
    }

    @Test
    void testDeleteCustomer() {
        var customerEntity = new CustomerEntity();
        customerEntity.setId(id);
        when(customerRepository.findById(id)).thenReturn(Optional.of(customerEntity));

        customerService.deleteCustomer(id);

        verify(customerRepository).save(customerEntityCaptor.capture());
        assertThat(customerEntityCaptor.getValue().getId()).isEqualTo(id);
        assertThat(customerEntityCaptor.getValue().isDeleted()).isEqualTo(true);
    }

    private CustomerEntity getCustomerEntity() {
        var customer = new CustomerEntity();
        customer.setId(id);
        customer.setTitle("Customer 1");
        customer.setCreatedAt(timestamp);
        customer.setModifiedAt(timestamp);
        return customer;
    }

    private CustomerDto getCustomerDto() {
        var customer = new CustomerDto();
        customer.setId(id);
        customer.setTitle("Customer 1");
        customer.setCreatedAt(timestamp);
        customer.setModifiedAt(timestamp);
        return customer;
    }
}
