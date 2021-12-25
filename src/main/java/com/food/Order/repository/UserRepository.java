package com.food.Order.repository;


import com.food.Order.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    User findByNumber(String username);
    boolean existsByNumber(String number);
    User findByuserId(String userId);
}
