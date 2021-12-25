package com.food.Order.repository;

import com.food.Order.entity.Order;
import com.food.Order.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,String> {
    @Query(value = "SELECT * FROM Order.orders where user_id=:userId",nativeQuery = true)
    List<Order> FindByUserId(String userId);
    Order findByorderId(String orderId);
}
