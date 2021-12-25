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
@Table(name = "user_details")
public class User {
    @Id
    @Column(name = "userId")
    private String userId;

    @Column(name = "number")
    private String number;

    @Column(name = "is_active")
    private boolean is_active;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;


    @Column(name = "address")
    private String address;

    @Column(name = "pinCode")
    private String pinCode;

    @CreationTimestamp
    @Column(name = "createdOn")
    private LocalDateTime createdOn;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE })
    @JsonManagedReference
    private List<Order> orders;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE })
    @JsonManagedReference
    private List<FvtItems> fvtItems;
}
