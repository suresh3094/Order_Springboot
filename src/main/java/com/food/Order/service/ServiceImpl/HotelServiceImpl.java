package com.food.Order.service.ServiceImpl;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.food.Order.constants.MessageCodes;
import com.food.Order.entity.Hotel;
import com.food.Order.entity.Item;
import com.food.Order.io.BaseResponse;
import com.food.Order.io.StatusMessage;
import com.food.Order.repository.HotelRepository;
import com.food.Order.repository.ItemRepository;
import com.food.Order.service.HotelService;
import com.food.Order.util.CommonUtils;
import com.food.Order.util.RandomIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class HotelServiceImpl implements HotelService {

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3 s3Client;

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    ItemRepository itemRepository;

    @Override
    public BaseResponse AddHotel(MultipartFile file, String HotelName, boolean active, String description, String createdBy, String updatedBy) throws Exception {
        File fileObj = convertMultiPartFileToFile(file);
        Hotel hotel=new Hotel();
        BeanUtils.copyProperties(HotelName,hotel);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName,fileName,fileObj));
        hotel.setHotelId(RandomIdGenerator.generateRandomAplhaNumericString(5));
        hotel.setImage_url("https://hotel-management.s3-ap-southeast-1.amazonaws.com/"+fileName);
        hotel.set_active(true);
        hotel.setDescription(description);
        hotel.setCreatedOn(LocalDateTime.now());
        hotel.setHotelName(HotelName);
        fileObj.delete();
        hotelRepository.save(hotel);
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data(hotel)
                .build();
    }

    @Override
    public BaseResponse getHotelId(String HotelId) throws Exception {
        Hotel hotel=hotelRepository.findByHotelId(HotelId);
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data(hotel)
                .build();

    }

    @Override
    public BaseResponse getAllHotel() throws Exception {
        List<Hotel> hotels=hotelRepository.findAll();
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data(hotels)
                .build();
    }

    @Override
    public BaseResponse getOffers() throws Exception {
        List<Item> items=itemRepository.FindByOffers();
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data(items)
                .build();
    }

    @Override
    public BaseResponse UpdateHotel(MultipartFile file, String HotelName, boolean active, String description, String updatedBy, String HotelId) throws Exception {
        Hotel hotel=hotelRepository.findByHotelId(HotelId);
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName,fileName,fileObj));
        hotel.setImage_url("https://hotel-management.s3-ap-southeast-1.amazonaws.com/"+fileName);
        hotel.set_active(true);
        hotel.setDescription(description);
        hotel.setCreatedOn(LocalDateTime.now());
        hotel.setHotelName(HotelName);
        fileObj.delete();
        hotelRepository.save(hotel);
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data(hotel)
                .build();

    }

    @Override
    public BaseResponse DeleteHotelm(String HotelId) throws Exception {
         hotelRepository.deleteById(HotelId);
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data("Deleted successfully"+HotelId)
                .build();
    }

    @Override
    public BaseResponse AddItem(MultipartFile file,String itemName, boolean active, String description, String createdBy,double Amount,String HotelId) throws Exception {
        Hotel hotel=hotelRepository.findByHotelId(HotelId);
        if (!CommonUtils.checkIsNullOrEmpty(hotel)){
            Item item=new Item();
            File fileObj = convertMultiPartFileToFile(file);
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            s3Client.putObject(new PutObjectRequest(bucketName,fileName,fileObj));
            item.setImage_url("https://hotel-management.s3-ap-southeast-1.amazonaws.com/"+fileName);
            item.setItemId(RandomIdGenerator.generateRandomAplhaNumericString(6));
            item.set_active(true);
            item.setAmount(Amount);
            item.setHotelName(hotel.getHotelName());
            item.setCreatedOn(LocalDateTime.now());
            item.setDescription(description);
            item.setItemName(itemName);
            item.setHotel(hotel);
            List<Item>items=hotel.getItems();
            items.add(item);
            hotel.setItems(items);
            hotelRepository.save(hotel);
            return BaseResponse.builder()
                    .status(MessageCodes.SUCCESS)
                    .statusMessage(StatusMessage.builder()
                            .code(MessageCodes.SUCCESS)
                            .description(MessageCodes.SUCCESS_DESC)
                            .build())
                    .data(item)
                    .build();

        }
        return null;
    }

    @Override
    public BaseResponse getItemId(String HotelId) throws Exception {
List<Item>items=itemRepository.FindByHotelId(HotelId);
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data(items)
                .build();
    }

    @Override
    public BaseResponse UpdateItem(MultipartFile file,String ItemName, boolean active, String description, String updatedBy,double Amont, String ItemId) throws Exception {
        Item item=itemRepository.findByItemId(ItemId);
        File fileObj = convertMultiPartFileToFile(file);
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName,fileName,fileObj));
        item.setImage_url("https://hotel-management.s3-ap-southeast-1.amazonaws.com/"+fileName);
        item.set_active(true);
        item.setDescription(description);
        item.setCreatedOn(LocalDateTime.now());
        item.setItemName(ItemName);
        item.setAmount(Amont);
        fileObj.delete();
        itemRepository.save(item);
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data(item)
                .build();
    }

    @Override
    public BaseResponse DeleteItem(String ItemId) throws Exception {
        itemRepository.deleteById(ItemId);
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data("Deleted successfully"+ItemId)
                .build();
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            log.error("Error converting multipartFile to file", e);
        }
        return convertedFile;
    }
}
