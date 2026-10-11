package com.prash.ecommerce.order_service.config;

import com.prash.ecommerce.order_service.dto.OrderRequest;
import com.prash.ecommerce.order_service.dto.OrderRequestItem;
import com.prash.ecommerce.order_service.entity.Orders;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderRequest toResponse(Orders orders);

    OrderRequestItem toResponseInItem(Orders orders);

    Orders toEntity(OrderRequest request);
}
