package com.example.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

    private String passportNumber;
    private String fullName;
    private Integer clientTypeOrdinal;
    private List<BuyingSimCardDto> buyingSimCardDtos;


}
