package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    private ServiceType serviceType;
    private Double amount;



//    @OneToOne
//    private Client client;

    @ManyToOne//bitta simkartaga ko'pgina detaillar to'g'ri keladi
    private SimCard simCard;


}
