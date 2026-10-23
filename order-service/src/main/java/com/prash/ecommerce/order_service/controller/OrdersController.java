package com.prash.ecommerce.order_service.controller;

import com.prash.ecommerce.order_service.client.InventoryFeignClient;
import com.prash.ecommerce.order_service.dto.OrderRequest;
import com.prash.ecommerce.order_service.service.OrdersService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrdersController {

    @Value("${my.variable}")
    private String myVariable;

    private final OrdersService orderService;
    private final InventoryFeignClient inventoryFeignClient;


    @GetMapping("/helloOrders")
    public String helloOrders() {
        return "Hello from Orders Service, calling variable:" + myVariable ;
    }

//    @PostMapping("/create-order")
//    public ResponseEntity<OrderRequest> createOrder(@RequestBody OrderRequest orderRequestDto) {
//        OrderRequest orderRequestDto1 = orderService.createOrder(orderRequestDto);
//        return ResponseEntity.ok(orderRequestDto1);
//    }

    @GetMapping
    public ResponseEntity<List<OrderRequest>> getAllOrders(HttpServletRequest httpServletRequest) {
        log.info("Fetching all orders via controller");
        List<OrderRequest> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);  // Returns 200 OK with the list of orders
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderRequest> getOrderById(@PathVariable Long id) {
        log.info("Fetching order with ID: {} via controller", id);
        OrderRequest order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);  // Returns 200 OK with the order
    }

    @PostMapping("/create-order")
    public ResponseEntity<OrderRequest> createOrder(@RequestBody OrderRequest request ){
        //Double reducedStocks = inventoryFeignClient.reduceStocks(request);
        return ResponseEntity.ok(orderService.createOrder(request));
    }
}
