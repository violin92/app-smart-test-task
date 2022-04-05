package com.appsmart.application.testtask.controller;

import com.appsmart.application.testtask.dto.CustomerDto;
import com.appsmart.application.testtask.dto.EditCustomerDto;
import com.appsmart.application.testtask.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    CustomerDto getCustomerById(@PathVariable("id") UUID id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    void addCustomer(@RequestBody CustomerDto customerDto) {
        customerService.addCustomer(customerDto);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    void updateCustomer(@PathVariable("id") UUID id, @RequestBody EditCustomerDto customerDto) {
        customerService.updateCustomer(id, customerDto);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    void deleteCustomer(@PathVariable("id") UUID id) {
        customerService.deleteCustomer(id);
    }

}
