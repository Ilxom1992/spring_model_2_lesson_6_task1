package com.example.demo.payload;

import com.example.demo.entity.Service;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class SimCardServiceDto {
    private Integer simCardId;
    private Integer serviceId;
    private boolean status=true;
    private Timestamp startDateTime;
    private Timestamp endDateTime;
}
