package com.food.Order.repository;

import com.food.Order.entity.Order;
import com.food.Order.entity.OrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderHeaderRepository extends JpaRepository<OrderHeader,String> {
}
