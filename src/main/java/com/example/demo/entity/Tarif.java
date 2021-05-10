package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tarif {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String description;
    private Double price;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @ManyToMany
    private Set<Detail> details;
    @ManyToMany
    private Set<Ussd> ussdSet;
    @Column(nullable = false)
    private String ussd;
    private Integer countDateOfExpire;

    private int mb; // tarif rejasi doirasida beriladigan mb miqdori
    private int sms; // tarif rejasi doirasida beriladigan sms miqdori
    private int min; // tarif rejasi doirasida beriladigan min miqdori
    private int mbCost; // mb tugaganda 1mb narxi
    private int smsCost; // sms tugaganda 1sms narxi
    private int minCost; // min tugaganda 1min narxi



}
