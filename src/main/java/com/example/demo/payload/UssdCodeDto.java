package com.example.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UssdCodeDto {
    private Integer companyCode;
    private Integer ussdCode;
    private String description;
}
