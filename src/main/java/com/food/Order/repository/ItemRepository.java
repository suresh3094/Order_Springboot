package com.food.Order.repository;


import com.food.Order.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item,String> {

    @Query(value = "SELECT * FROM Order.items where hotel_id=:HotelId",nativeQuery = true)
    List<Item> FindByHotelId(String HotelId);

    @Query(value = "SELECT hotel_name FROM Order.items where item_id=:itemId",nativeQuery = true)
    String FindByHotelName(String itemId);

    Item findByItemId(String HotelId);

    @Query(value = "SELECT hotel_id FROM Order.items where item_id=:itemId",nativeQuery = true)
    String findByHotelId(String itemId);

    @Query(value = "SELECT amount FROM Order.items where item_id=:itemId",nativeQuery = true)
    double FindByItemId(String itemId);

    @Query(value = "SELECT * FROM Order.items where offer>0",nativeQuery = true)
    List<Item> FindByOffers();

    @Query(value = "SELECT image_url FROM Order.items where item_id=:itemId",nativeQuery = true)
    String findByImage(String itemId);

    @Query(value = "SELECT item_name FROM Order.items where item_id=:itemId",nativeQuery = true)
    String findByItemName(String itemId);

    @Query(value = "SELECT item_id FROM Order.items where item_id=:itemId",nativeQuery = true)
    String findByItemsId(String itemId);

}
