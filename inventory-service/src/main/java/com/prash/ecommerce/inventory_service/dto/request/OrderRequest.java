package com.prash.ecommerce.inventory_service.dto.request;

import java.util.List;

public record OrderRequest(
        List<OrderRequestItem>items
) {
}
