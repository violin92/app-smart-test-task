package com.appsmart.application.testtask.mapper;

import com.appsmart.application.testtask.dto.CustomerDto;
import com.appsmart.application.testtask.model.CustomerEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = ProductMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CustomerMapper {

    CustomerDto toDto(CustomerEntity customerEntity);

    CustomerEntity toEntity(CustomerDto customerDto);

    List<CustomerDto> toListOfDtos(List<CustomerEntity> listOfEntities);

}
