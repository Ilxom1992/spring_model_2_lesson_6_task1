package com.example.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TarifDto {
    private String title;
    private String description;
    private Double price;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
