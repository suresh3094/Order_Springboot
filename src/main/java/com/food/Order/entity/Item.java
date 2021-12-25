package com.food.Order.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "items")
public class Item {
    @Id
    @Column(name = "itemId")
    private String itemId;

    @Column(name = "itemName")
    private String itemName;

    @Column(name = "is_active")
    private boolean is_active;

    @Column(name = "image_url")
    private String image_url;

    @Column(name = "description")
    private String description;

    @Column(name = "offer")
    private double offer;

    @Column(name = "hotelName")
    private String hotelName;

    @Column(name = "Amount")
    private double Amount;

    @CreationTimestamp
    @Column(name = "createdOn")
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "updated_on")
    private Date updatedOn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hotelId")
    @JsonBackReference
    private Hotel hotel;
}
