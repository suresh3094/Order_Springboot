package com.food.Order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {

    private String orderId;
    private String itemId;
    private String hotelId;
    private double totalAmount;
    private boolean is_active;
    private double quantity;
    private double itemAmount;
    private double totAmt;
}
