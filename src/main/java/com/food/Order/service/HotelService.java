package com.food.Order.service;

import com.food.Order.io.BaseResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface HotelService {
    BaseResponse AddHotel(MultipartFile file,String hotelName,boolean active,String description,String createdBy,String updatedBy) throws Exception;
    BaseResponse getHotelId(String HotelId) throws Exception;
    BaseResponse getAllHotel() throws Exception;
    BaseResponse getOffers()throws Exception;
    BaseResponse UpdateHotel(MultipartFile file,String hotelName,boolean active,String description,String updatedBy,String HotelId) throws Exception;
    BaseResponse DeleteHotelm(String HotelId) throws Exception;
    BaseResponse AddItem(MultipartFile file,String itemName,boolean active,String description,String createdBy,double Amount,String HotelId) throws Exception;
    BaseResponse getItemId(String HotelId) throws Exception;
    BaseResponse UpdateItem(MultipartFile file,String ItemName,boolean active,String description,String updatedBy,double Amount,String ItemId) throws Exception;
    BaseResponse DeleteItem(String ItemId) throws Exception;
}
