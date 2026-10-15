package com.prash.ecommerce.order_service.service;

import com.prash.ecommerce.order_service.client.InventoryFeignClient;
import com.prash.ecommerce.order_service.config.OrderMapper;
import com.prash.ecommerce.order_service.config.OrderStatus;
import com.prash.ecommerce.order_service.dto.OrderRequest;
import com.prash.ecommerce.order_service.dto.OrderRequestItem;
import com.prash.ecommerce.order_service.entity.OrderItem;
import com.prash.ecommerce.order_service.entity.Orders;
import com.prash.ecommerce.order_service.repository.OrdersRepository;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final OrderMapper orderMapper;
    private final InventoryFeignClient inventoryOpenFeignClient;


    public List<OrderRequest> getAllOrders(){
        List<Orders> ordersList = ordersRepository.findAll();

       return  ordersList.stream().map(orders -> orderMapper.toResponse(orders)).toList();
    }

    public OrderRequest getOrderById(Long id){
        Orders orders = ordersRepository.findById(id).orElseThrow(() -> new RuntimeException("No order found using Id:" + id));

        return orderMapper.toResponse(orders);
    }

    @Transactional
    @Retry(name = "inventoryRetry", fallbackMethod = "createOrderFallback")
    @RateLimiter(name = "inventoryRateLimiter", fallbackMethod = "createOrderFallback")
    public OrderRequest createOrder(OrderRequest request) {
        log.info("Create order service has been called");
        Double totalPrice= inventoryOpenFeignClient.reduceStocks(request);

        Orders orders = orderMapper.toEntity(request);

        for(OrderItem items: orders.getItems()){
            items.setOrder(orders);
        }
        orders.setTotalPrice(totalPrice);
        orders.setStatus(OrderStatus.CONFIRMED);
        Orders savedOrders = ordersRepository.save(orders);

        return orderMapper.toResponse(savedOrders);

    }

    public OrderRequest createOrderFallback(OrderRequest request, Throwable throwable){
        log.info("Fackback has called due to some issues{}", throwable);
        return new OrderRequest(null,null, null);
    }





}
