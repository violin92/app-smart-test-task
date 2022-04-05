package com.appsmart.application.testtask.mapper;

import com.appsmart.application.testtask.dto.ProductDto;
import com.appsmart.application.testtask.model.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    @Mapping(target = "customer.products", ignore = true)
    ProductDto toDto(ProductEntity productEntity);

    ProductEntity toEntity(ProductDto productDto);

    List<ProductDto> toListOfDtos(List<ProductEntity> listOfEntities);

}
