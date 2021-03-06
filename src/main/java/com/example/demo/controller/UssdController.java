package com.example.demo.controller;

import com.example.demo.entity.Ussd;
import com.example.demo.service.UssdService;
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
@RequestMapping("/ussd")
public class UssdController {
    final UssdService ussdService;

    public UssdController(UssdService ussdService) {
        this.ussdService = ussdService;
    }

    //  CREATE
    @PostMapping
    public HttpEntity<?> addUssd(@Valid @RequestBody Ussd ussd){
        return ResponseEntity.status(200).body(ussdService.add(ussd));
    }
    //READ
    @GetMapping
    public HttpEntity<?> getUssd(){
        return ResponseEntity.status(200).body(ussdService.get());
    }
    //UPDATE
    @PutMapping(value = "/{id}")
    public HttpEntity<?> editUssd(@Valid @RequestBody Ussd ussd,@Valid @PathVariable Integer id){
        return ResponseEntity.status(200).body(ussdService.edit(id,ussd));
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
