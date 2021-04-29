package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SimCardTariff {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private SimCard simCard;

    @ManyToOne
    private Tarif tariff;

    private boolean status = true;

    @Column(nullable = false)
    @CreationTimestamp
    private Timestamp dateOfPurchase;

    @Transient
    private Timestamp expireDate;

    // Ta'rifning Tugash sana va vaqtini ko'rish uchun
    public Timestamp getExpireDate() {
        LocalDateTime localDateTimeOfPurchase = dateOfPurchase.toLocalDateTime();
        return Timestamp.valueOf(localDateTimeOfPurchase.plusDays((long) tariff.getCountDateOfExpire()));
    }
}
