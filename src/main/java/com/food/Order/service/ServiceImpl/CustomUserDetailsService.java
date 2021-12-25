package com.food.Order.service.ServiceImpl;


import com.food.Order.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String number) throws UsernameNotFoundException {
        com.food.Order.entity.User user =repository.findByNumber(number);
        return new org.springframework.security.core.userdetails.User(user.getNumber(), user.getPassword(), new ArrayList<>());
    }
}
