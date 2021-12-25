package com.food.Order.repository;

import com.food.Order.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel,String> {
    Hotel findByHotelId(String HotelId);


}
