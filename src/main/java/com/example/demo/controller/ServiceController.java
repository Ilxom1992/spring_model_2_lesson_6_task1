package com.example.demo.controller;

import com.example.demo.payload.ServiceDto;
import com.example.demo.service.Services;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/service")
public class ServiceController {
   final Services services;

    public ServiceController(Services services) {
        this.services = services;
    }
    //  CREATE
    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody ServiceDto serviceDto){
        return ResponseEntity.status(200).body(services.add(serviceDto));
    }
    //READ
    @GetMapping
    public HttpEntity<?> get(){
        return ResponseEntity.status(200).body(services.get());
    }
    //UPDATE
    @PutMapping(value = "/{id}")
    public HttpEntity<?> edit(@Valid @RequestBody ServiceDto serviceDto,@Valid @PathVariable Integer id){
        return ResponseEntity.status(200).body(services.edit(id,serviceDto));
    }
    //DELETE
    @DeleteMapping(value = "/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        return ResponseEntity.ok(services.delete(id));
    }
    //READ BY ID
    @GetMapping(value = "/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id){
        return ResponseEntity.ok(services.getById(id));
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
