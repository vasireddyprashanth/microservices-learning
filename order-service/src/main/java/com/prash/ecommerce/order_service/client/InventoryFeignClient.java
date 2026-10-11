package com.prash.ecommerce.order_service.client;

import com.prash.ecommerce.order_service.dto.OrderRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "inventory-service", path = "/api/v1/products")
public interface InventoryFeignClient {

    @PostMapping("/reduce-stocks")
    public Double reduceStocks(@RequestBody OrderRequest orderRequest);
}
