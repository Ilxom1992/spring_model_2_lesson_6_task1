package com.example.demo.controller;

import com.example.demo.entity.ServiceType;
import com.example.demo.payload.ServiceTypeDto;
import com.example.demo.service.ServiceTypeService;
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
@RequestMapping("/serviceType")
public class ServiceTypeController {
    final ServiceTypeService serviceTypeService;
    public ServiceTypeController(ServiceTypeService serviceTypeService) {
        this.serviceTypeService = serviceTypeService;
    }
    //  CREATE
    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody ServiceType serviceTypeDto){
        return ResponseEntity.status(200).body(serviceTypeService.addServiceType(serviceTypeDto));
    }
    //READ
    @GetMapping
    public HttpEntity<?> get(){
        return ResponseEntity.status(200).body(serviceTypeService.getServiceType());
    }
    //UPDATE
    @PutMapping(value = "/{id}")
    public HttpEntity<?> edit(@Valid @RequestBody ServiceTypeDto serviceTypeDto,@PathVariable Integer id ){
        return ResponseEntity.status(200).body(serviceTypeService.editServiceType(id,serviceTypeDto));
    }
    //DELETE
    @DeleteMapping(value = "/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        return ResponseEntity.ok(serviceTypeService.deleteServiceType(id));
    }
    //READ BY ID
    @GetMapping(value = "/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id){
        return ResponseEntity.ok(serviceTypeService.getByIdServiceType(id));
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
