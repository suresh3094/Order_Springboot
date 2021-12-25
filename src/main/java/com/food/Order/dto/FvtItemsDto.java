package com.food.Order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FvtItemsDto {
    private String fvtId;
    private String itemId;
    private boolean is_active;
}
