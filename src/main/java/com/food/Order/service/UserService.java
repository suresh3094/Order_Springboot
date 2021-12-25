package com.food.Order.service;

import com.food.Order.dto.FilterDto;
import com.food.Order.dto.FvtItemsDto;
import com.food.Order.dto.OrderDto;
import com.food.Order.dto.UserDto;
import com.food.Order.io.BaseResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    BaseResponse CreateUser(UserDto userDto) throws Exception;
    BaseResponse Login(UserDto userDto) throws Exception;
    BaseResponse orders(String userId,Double totAmt, List<OrderDto> orderDtos);
    BaseResponse getUserId(String userId) throws Exception;
    BaseResponse updateUser(String userId,UserDto userDto)throws Exception;
    BaseResponse FilterItems(FilterDto filterDto)throws Exception;
    BaseResponse getOrders(String userId) throws Exception;
    BaseResponse updatePassword(String userId,UserDto userDto) throws Exception;
    BaseResponse updateFvtItem(String userId, FvtItemsDto fvtItemsDto)throws Exception;
}
