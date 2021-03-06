package com.example.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ServiceDto {
    private String serviceName;
    private String ussdCode;
    private Integer serviceTypeId;
    private String description;
    private List<Integer> ussdId;
    private Integer detailId;
    private double price;
}
