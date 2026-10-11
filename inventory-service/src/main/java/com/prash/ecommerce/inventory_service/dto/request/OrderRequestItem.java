package com.prash.ecommerce.inventory_service.dto.request;

public record OrderRequestItem(
        Long productId,
        Integer quantity
) {
}
