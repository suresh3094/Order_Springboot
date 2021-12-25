package com.food.Order.entity;

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
@Table(name = "hotel")
public class Hotel {
    @Id
    @Column(name = "hotelId")
    private String hotelId;

    @Column(name = "hotelName")
    private String hotelName;

    @Column(name = "is_active")
    private boolean is_active;

    @Column(name = "image_url")
    private String image_url;

    @Column(name = "description")
    private String description;

    @CreationTimestamp
    @Column(name = "createdOn")
    private LocalDateTime createdOn;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    @Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE })
    @JsonManagedReference
    private List<Item> items;
}
