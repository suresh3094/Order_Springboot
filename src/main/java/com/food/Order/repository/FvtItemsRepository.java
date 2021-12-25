package com.food.Order.repository;

import com.food.Order.entity.FvtItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FvtItemsRepository extends JpaRepository<FvtItems, String> {
    @Query(value = "SELECT * FROM Order.fvt_items where item_id=:itemId and user_id=:userId",nativeQuery = true)
    FvtItems fvtId(String itemId,String userId);
}
