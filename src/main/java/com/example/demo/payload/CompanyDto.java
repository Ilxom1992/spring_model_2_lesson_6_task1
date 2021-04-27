package com.example.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CompanyDto {
    private String name;
    private Integer userId;
    private Integer companyId;

    private String region;
    private String district;
    private String street;
    private Integer houseNumber;
}
