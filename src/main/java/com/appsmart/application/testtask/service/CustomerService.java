package com.appsmart.application.testtask.service;

import com.appsmart.application.testtask.dto.CustomerDto;
import com.appsmart.application.testtask.dto.EditCustomerDto;
import com.appsmart.application.testtask.mapper.CustomerMapper;
import com.appsmart.application.testtask.model.CustomerEntity;
import com.appsmart.application.testtask.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public List<CustomerDto> getAllCustomers() {
        List<CustomerEntity> customers = customerRepository.findAll().stream()
                .filter(customer -> !customer.isDeleted())
                .collect(Collectors.toList());
        return customerMapper.toListOfDtos(customers);
    }

    public CustomerDto getCustomerById(UUID id) {
        var customerOptional = customerRepository.findById(id);
        if (customerOptional.isPresent() && !customerOptional.get().isDeleted()) {
            return customerMapper.toDto(customerOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void addCustomer(CustomerDto customerDto) {
        var customerEntity = customerMapper.toEntity(customerDto);
        customerRepository.save(customerEntity);
    }

    public void updateCustomer(UUID id, EditCustomerDto customerDto) {
        var customerOptional = customerRepository.findById(id);
        if (customerOptional.isPresent() && !customerOptional.get().isDeleted()) {
            if (customerDto.getTitle() != null) {
                customerOptional.get().setTitle(customerDto.getTitle());
                customerRepository.save(customerOptional.get());
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    public void deleteCustomer(UUID id) {
        var customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        customer.setDeleted(true);
        customerRepository.save(customer);
    }
}
