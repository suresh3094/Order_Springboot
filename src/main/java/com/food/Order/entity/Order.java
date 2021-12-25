package com.food.Order.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "orderId")
    private String orderId;

    @Column(name = "itemId")
    private String itemId;

    @Column(name = "hotelId")
    private String hotelId;

    @Column(name = "hotelName")
    private String hotelName;

    @Column(name = "itemName")
    private String itemName;

    @Column(name = "totalAmount")
    private double totalAmount;

    @Column(name = "is_active")
    private boolean is_active;

    @Column(name = "quantity")
    private double quantity;

    @Column(name = "image_url")
    private String image_url;

    @Column(name = "itemAmount")
    private double itemAmount;

    @CreationTimestamp
    @Column(name = "createdOn")
    private LocalDateTime createdOn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE })
    @JsonManagedReference
    private List<OrderHeader> orderHeaders;
}
