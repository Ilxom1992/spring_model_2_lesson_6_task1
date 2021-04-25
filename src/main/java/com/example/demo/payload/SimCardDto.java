package com.example.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SimCardDto {
    private Long phoneNumber;
    private Integer userId;
    private Integer companyId;
}
