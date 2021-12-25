package com.food.Order.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders_header")
public class OrderHeader {
    @Id
    @Column(name = "totalAmountId")
    private String totalAmountId;

    @Column(name = "hotelId")
    private String hotelId;

    @Column(name = "hotelName")
    private String hotelName;

    @Column(name = "totAmt")
    private double totAmt;

    @Column(name = "is_active")
    private boolean is_active;

    @CreationTimestamp
    @Column(name = "createdOn")
    private LocalDateTime createdOn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orderId")
    @JsonBackReference
    private Order order;

}
