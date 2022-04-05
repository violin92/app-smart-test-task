package com.appsmart.application.testtask.controller;

import com.appsmart.application.testtask.dto.CustomerDto;
import com.appsmart.application.testtask.dto.EditCustomerDto;
import com.appsmart.application.testtask.service.CustomerService;
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
class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private List<CustomerDto> customers;

    @Mock
    private CustomerDto customer;

    @Mock
    private EditCustomerDto editCustomer;

    @Captor
    private ArgumentCaptor<CustomerDto> customerDtoCaptor;

    private CustomerController customerController;

    private final UUID id = UUID.randomUUID();

    @BeforeEach
    void init() {
        customerController = new CustomerController(customerService);
    }

    @Test
    void testGetAllCustomers() {
        when(customerService.getAllCustomers()).thenReturn(customers);
        var result = customerController.getAllCustomers();
        assertEquals(customers, result);
    }

    @Test
    void testGetCustomerById() {
        when(customerService.getCustomerById(id)).thenReturn(customer);
        var result = customerController.getCustomerById(id);
        assertEquals(customer, result);
    }

    @Test
    void testAddCustomer() {
        customerController.addCustomer(customer);
        verify(customerService).addCustomer(customerDtoCaptor.capture());
        assertEquals(customer, customerDtoCaptor.getValue());
    }

    @Test
    void testUpdateCustomer() {
        var editCustomerCaptor = ArgumentCaptor.forClass(EditCustomerDto.class);
        customerController.updateCustomer(id, editCustomer);
        verify(customerService).updateCustomer(eq(id), editCustomerCaptor.capture());
        assertEquals(editCustomer, editCustomerCaptor.getValue());
    }

    @Test
    void testDeleteCustomer() {
        customerController.deleteCustomer(id);
        verify(customerService).deleteCustomer(id);
    }

}
