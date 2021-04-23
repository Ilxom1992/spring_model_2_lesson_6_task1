package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String serviceName;

    private String  ussdCode;

    @ManyToOne
    private ServiceType serviceType;

    private String description;

    @ManyToMany
    private Set<Detail> detailSet;

    @ManyToMany
    private Set<Ussd> ussdSet;

}
