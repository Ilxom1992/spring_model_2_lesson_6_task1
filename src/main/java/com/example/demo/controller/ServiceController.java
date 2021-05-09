package com.example.demo.controller;

import com.example.demo.entity.ServiceType;
import com.example.demo.payload.DetailDto;
import com.example.demo.payload.Response;
import com.example.demo.payload.ServiceDto;
import com.example.demo.payload.TarifDto;
import com.example.demo.service.Services;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/service")
public class ServiceController {
   final Services serviceSvc;

    public ServiceController(Services serviceSvc) {
        this.serviceSvc = serviceSvc;
    }


    @PostMapping
    public HttpEntity<?> saveService(@RequestBody ServiceDto serviceDto) {
        Response response = serviceSvc.saveService(serviceDto);
        return ResponseEntity.status(response.isStatus() ? 201 : 401).body(response);
    }

    @PostMapping("/tariff")
    public HttpEntity<?> saveTariff(@RequestBody TarifDto tariffDto) {
        Response response = serviceSvc.saveTariff(tariffDto);
        return ResponseEntity.status(response.isStatus() ? 201 : 401).body(response);
    }

    @PostMapping("/serviceType")
    public HttpEntity<?> saveServiceType(@RequestBody ServiceType serviceType) {
        Response response = serviceSvc.saveServiceType(serviceType);
        return ResponseEntity.status(response.isStatus() ? 201 : 401).body(response);
    }

    @PutMapping("/serviceType/{id}")
    public HttpEntity<?> editServiceType(@PathVariable(name = "id") Integer serviceTypeId, @RequestBody ServiceType serviceType) {
        Response response = serviceSvc.editServiceType(serviceTypeId, serviceType);
        return ResponseEntity.status(response.isStatus() ? HttpStatus.ACCEPTED : HttpStatus.UNAUTHORIZED).body(response);
    }

    @DeleteMapping("/serviceType/{id}")
    public HttpEntity<?> deleteServiceType(@PathVariable(name = "id") Integer serviceTypeId) {
        Response response = serviceSvc.deleteServiceType(serviceTypeId);
        return ResponseEntity.status(response.isStatus() ? HttpStatus.NO_CONTENT : HttpStatus.UNAUTHORIZED).body(response);
    }

    @PostMapping("/serviceDetail")
    public HttpEntity<?> saveServiceDetail(@RequestBody DetailDto detailDto) {
        Response response = serviceSvc.saveServiceDetail(detailDto);
        return ResponseEntity.status(response.isStatus() ? 201 : 401).body(response);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
