package com.prash.ecommerce.order_service.dto;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        Long id,
        List<OrderRequestItem> items,
        BigDecimal totalPrice
) {
}
