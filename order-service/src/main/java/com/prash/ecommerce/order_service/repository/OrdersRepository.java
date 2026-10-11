package com.prash.ecommerce.order_service.repository;

import com.prash.ecommerce.order_service.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Long> {
}
