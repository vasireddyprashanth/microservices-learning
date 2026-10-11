package com.prash.ecommerce.inventory_service.config;

import com.prash.ecommerce.inventory_service.dto.ProductResponse;
import com.prash.ecommerce.inventory_service.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductResponse toResponse(Product product);
    Product toEntity(ProductResponse response);
}
