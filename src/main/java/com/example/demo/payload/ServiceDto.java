package com.example.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ServiceDto {
    private String serviceName;
    private String ussdCode;
    private Integer serviceTypeId;
    private String description;
}
