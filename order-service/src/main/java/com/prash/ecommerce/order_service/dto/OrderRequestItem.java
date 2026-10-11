package com.prash.ecommerce.order_service.dto;

public record OrderRequestItem(

        Long id,
        Long productId,
        Integer quantity
) {
}
