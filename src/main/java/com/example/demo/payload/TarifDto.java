package com.example.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TarifDto {
    private String title;
    private String description;
    private Double price;
    @Column(nullable = false)
    private String ussd;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Set<Integer> detailIdSet;
    private List<Integer> tarifUssdId;
    private Integer countDateOfExpire;}
