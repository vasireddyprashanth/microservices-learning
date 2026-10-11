package com.prash.ecommerce.inventory_service.dto;

import lombok.Builder;

@Builder
public record ProductResponse(

        String title,
        Double price,
        Long stock
) {
}
