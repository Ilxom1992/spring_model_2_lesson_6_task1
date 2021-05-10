package com.example.demo.entity;

import com.example.demo.model.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class DebtSimCard extends AbsEntity {

    @ManyToOne
    private SimCard simCard;

    private Timestamp expireDate;
    private double amount;


}
