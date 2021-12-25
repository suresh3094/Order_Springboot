package com.food.Order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private String userId;
    private String number;
    private String password;
    private String name;
    private String pinCode;
    private String Token;
    private String oldPassword;
    private boolean is_active;
    private String address;

}
