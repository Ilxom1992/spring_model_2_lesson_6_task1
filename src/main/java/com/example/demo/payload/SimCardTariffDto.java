package com.example.demo.payload;

import lombok.Data;

@Data
public class SimCardTariffDto {

    private Integer simCardId;
    private Integer tariffId;
    private boolean status;
}
