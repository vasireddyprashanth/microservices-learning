package com.prash.ecommerce.inventory_service.service;

import com.prash.ecommerce.inventory_service.config.ProductMapper;
import com.prash.ecommerce.inventory_service.dto.ProductResponse;
import com.prash.ecommerce.inventory_service.dto.request.OrderRequest;
import com.prash.ecommerce.inventory_service.dto.request.OrderRequestItem;
import com.prash.ecommerce.inventory_service.entity.Product;
import com.prash.ecommerce.inventory_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public List<ProductResponse> getAllInventory(){
        log.info("Fetching all inventory items");
        List<Product> inventories = productRepository.findAll();

        return   inventories.stream().map(product -> productMapper.toResponse(product)).toList();
    }

    public ProductResponse getProductById(Long id){
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("No product found with Id:" + id));

        return productMapper.toResponse(product);
    }

    @Transactional(timeout = 2)
    public Double reduceStocks(OrderRequest request) {
        Double totalPrice= 0.0;
        for(OrderRequestItem item: request.items()){
            Long productId = item.productId();
            Integer quantity = item.quantity();

            Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("No product found with ID:" + productId));
            if(quantity>product.getStock()){
                throw new RuntimeException("Order cannot be fullfilled as quantity is less than requested");

            }

            product.setStock(product.getStock()-quantity);
            productRepository.save(product);
            totalPrice+= quantity*product.getPrice();


        }
        return totalPrice;
    }
}
