package com.example.demo.payload;

import com.example.demo.entity.SimCard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DetailDto {
    private Integer serviceTypeId;
    private Double amount;
    private SimCard simCard;
}
