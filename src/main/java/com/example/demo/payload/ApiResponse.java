package com.example.demo.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private String message;
    private boolean status;
    private Object object;

    public ApiResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
    }
}
