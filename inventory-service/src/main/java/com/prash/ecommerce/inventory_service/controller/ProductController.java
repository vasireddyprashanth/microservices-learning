package com.prash.ecommerce.inventory_service.controller;

import com.prash.ecommerce.inventory_service.client.OrdersFeignClient;
import com.prash.ecommerce.inventory_service.dto.ProductResponse;
import com.prash.ecommerce.inventory_service.dto.request.OrderRequest;
import com.prash.ecommerce.inventory_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final RestClient restClient;
    private final DiscoveryClient discoveryClient;
    private final OrdersFeignClient ordersFeignClient;

    @GetMapping("/fetchOrders")
    public String fetchOrders(){
        ServiceInstance orderService = discoveryClient.getInstances("order-service").stream().findFirst().orElseThrow(() -> new IllegalArgumentException("No URL found"));
//       return restClient.get()
//                .uri(orderService.getUri()+"/api/v1/orders/helloOrders")
//               .retrieve()
//                .body(String.class);
        return ordersFeignClient.helloOrders();

    }

    @GetMapping()
    public ResponseEntity<List<ProductResponse>> getAllInventory(){
        return ResponseEntity.ok().body(
                productService.getAllInventory()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id){
        return ResponseEntity.ok().body(
                productService.getProductById(id)
        );
    }

    @PostMapping("reduce-stocks")
    public ResponseEntity<Double> reduceStocks(@RequestBody OrderRequest request)  {
       Double totalPrice= productService.reduceStocks(request);
       return ResponseEntity.ok(totalPrice);
    }


}
