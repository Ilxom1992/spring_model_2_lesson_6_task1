package com.example.demo.entity;

import com.example.demo.entity.SimCard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Payment {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private SimCard simCard;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String paymentType;

    @Column(nullable = false)
    private String owner;

    @CreationTimestamp
    private Timestamp created_at;

}
