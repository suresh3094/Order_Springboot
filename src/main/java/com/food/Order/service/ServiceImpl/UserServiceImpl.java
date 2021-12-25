package com.food.Order.service.ServiceImpl;

import com.food.Order.constants.MessageCodes;
import com.food.Order.dto.FilterDto;
import com.food.Order.dto.FvtItemsDto;
import com.food.Order.dto.OrderDto;
import com.food.Order.dto.UserDto;
import com.food.Order.entity.*;
import com.food.Order.io.BaseResponse;
import com.food.Order.io.StatusMessage;
import com.food.Order.repository.*;
import com.food.Order.service.UserService;
import com.food.Order.util.CommonUtils;
import com.food.Order.util.JwtUtil;
import com.food.Order.util.RandomIdGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderHeaderRepository orderHeaderRepository;

    @Autowired
    FvtItemsRepository fvtItemsRepository;

    @Override
    public BaseResponse CreateUser(UserDto userDto) throws Exception {
        if(userRepository.existsByNumber(userDto.getNumber()))
            throw new IllegalArgumentException("This Number alresdy exists");
        User user=new User();
        BeanUtils.copyProperties(userDto,user);
        user.setUserId(RandomIdGenerator.generateRandomAplhaNumericString(6));
        user.setCreatedOn(LocalDateTime.now());
        user.set_active(true);
        userRepository.save(user);
        return BaseResponse.builder().status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build()).data(user).build();
    }

    @Override
    public BaseResponse Login(UserDto userDto) throws Exception {
        User userDetails=userRepository.findByNumber(userDto.getNumber());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userDto.getNumber(),userDto.getPassword()));
        } catch (Exception ex) {
            throw new IllegalArgumentException("inavalid username/password");
        }
        UserDto userDto1=new UserDto();
        BeanUtils.copyProperties(userDetails,userDto1);
        userDto1.setPassword(null);
        userDto1.setToken(jwtUtil.generateToken(userDto.getNumber()));
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data(userDto1)
                .build();
    }

    @Override
    public BaseResponse orders(String userId,Double totAmt, List<OrderDto> orderDtos) {
        User user=userRepository.findByuserId(userId);
        if (!CommonUtils.checkIsNullOrEmpty(user)) {
            List<Order> orders = new ArrayList<>();
            for (OrderDto orderDto : orderDtos) {
                Order order = new Order();
                OrderHeader orderHeader= new OrderHeader();
                String item=itemRepository.findByHotelId(orderDto.getItemId());
                String image=itemRepository.findByImage(orderDto.getItemId());
                String itemName=itemRepository.findByItemName(orderDto.getItemId());
                double item1=itemRepository.FindByItemId(orderDto.getItemId());
                String hotelName=itemRepository.FindByHotelName(orderDto.getItemId());
                double amt=item1*orderDto.getQuantity();
                order.set_active(true);
                order.setCreatedOn(LocalDateTime.now());
                order.setOrderId(RandomIdGenerator.generateRandomAplhaNumericString(6));
                order.setTotalAmount(amt);
                order.setHotelId(item);
                order.setHotelName(hotelName);
                order.setItemName(itemName);
                order.setItemAmount(item1);
                order.setImage_url(image);
                order.setItemId(orderDto.getItemId());
                order.setQuantity(orderDto.getQuantity());
                order.setUser(user);
                orders.add(order);
                user.setOrders(orders);
                orderHeader.setTotalAmountId(RandomIdGenerator.generateRandomAplhaNumericString(6));
                orderHeader.setTotAmt(totAmt);
                orderHeader.set_active(true);
                orderHeader.setHotelId(item);
                orderHeader.setHotelName(hotelName);
                orderHeader.setOrder(order);
                userRepository.save(user);
                orderHeaderRepository.save(orderHeader);
            }
        }
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data("Order successfully placed")
                .build();

    }

    @Override
    public BaseResponse getUserId(String userId) throws Exception {
        User user=userRepository.findByuserId(userId);
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data(user)
                .build();
    }

    @Override
    public BaseResponse updateUser(String userId, UserDto userDto) throws Exception {
        User user=userRepository.findByuserId(userId);
        BeanUtils.copyProperties(userDto,user, CommonUtils.getNullPropertyNames(userDto));
        userRepository.save(user);
        user.setPassword(null);
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data(user)
                .build();
    }

    @Override
    public BaseResponse FilterItems(FilterDto filterDto) throws Exception {

        List<Item> items=null;
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Item> cq = cb.createQuery(Item.class);
        Root<Item> book = cq.from(Item.class);
        List<Predicate> predicates = new ArrayList<>();

        if(!CommonUtils.checkIsNullOrEmpty(filterDto.getItemName())) {
            predicates.add(cb.like(book.get("itemName"), "%"+filterDto.getItemName()+"%"));
        }
        cq.where(predicates.toArray(new Predicate[0]));
        TypedQuery<Item> query = entityManager.createQuery(cq);
        items= query.getResultList();
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
    public BaseResponse getOrders(String userId) throws Exception {
List<Order> orders=orderRepository.FindByUserId(userId);
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data(orders)
                .build();
    }

    @Override
    public BaseResponse updatePassword(String userId, UserDto userDto) throws Exception {
        User user=userRepository.findByuserId(userId);
       // BeanUtils.copyProperties(userDto,user, CommonUtils.getNullPropertyNames(userDto));
        String old_Password=user.getPassword();
        if(userDto.getOldPassword().equals(old_Password))
        {
            user.setPassword(userDto.getPassword());
            userRepository.save(user);
        }
        else {
            return BaseResponse.builder()
                    .status(MessageCodes.SUCCESS)
                    .statusMessage(StatusMessage.builder()
                            .code(MessageCodes.SUCCESS)
                            .description(MessageCodes.SUCCESS_DESC)
                            .build())
                    .data("Please Check Old Password")
                    .build();
        }
        return BaseResponse.builder()
                .status(MessageCodes.SUCCESS)
                .statusMessage(StatusMessage.builder()
                        .code(MessageCodes.SUCCESS)
                        .description(MessageCodes.SUCCESS_DESC)
                        .build())
                .data("Password Chenge Succesfully")
                .build();
    }

    @Override
    public BaseResponse updateFvtItem(String userId, FvtItemsDto fvtItemsDto) throws Exception {
        User user=userRepository.findByuserId(userId);
        FvtItems fvtId=fvtItemsRepository.fvtId(fvtItemsDto.getItemId(),userId);
        FvtItems fvtItems=new FvtItems();
        List<FvtItems> fvtItemsList = new ArrayList<>();
        if (!CommonUtils.checkIsNullOrEmpty(fvtId)){
            fvtItemsRepository.deleteById(fvtId.getFvtId());
            return BaseResponse.builder()
                    .status(MessageCodes.SUCCESS)
                    .statusMessage(StatusMessage.builder()
                            .code(MessageCodes.SUCCESS)
                            .description(MessageCodes.SUCCESS_DESC)
                            .build())
                    .data("Removed Favorite Food")
                    .build();
        }
        else {
            fvtItems.setFvtId(RandomIdGenerator.generateRandomAplhaNumericString(6));
            fvtItems.setItemId(fvtItemsDto.getItemId());
            fvtItems.set_active(true);
            fvtItems.setUser(user);
            fvtItemsList.add(fvtItems);
            user.setFvtItems(fvtItemsList);
            userRepository.save(user);
            return BaseResponse.builder()
                    .status(MessageCodes.SUCCESS)
                    .statusMessage(StatusMessage.builder()
                            .code(MessageCodes.SUCCESS)
                            .description(MessageCodes.SUCCESS_DESC)
                            .build())
                    .data("Added Favorite Food")
                    .build();
        }
    }

}
