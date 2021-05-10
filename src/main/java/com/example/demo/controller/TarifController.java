package com.example.demo.controller;

import com.example.demo.entity.Tarif;
import com.example.demo.payload.TarifDto;
import com.example.demo.service.TarifService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/tarif")
public class TarifController {
    final TarifService tarifService;

    public TarifController(TarifService tarifService) {
        this.tarifService = tarifService;
    }

    //  CREATE
    @PostMapping
    public HttpEntity<?> addTariff(@Valid @RequestBody TarifDto tarifDto){
        return ResponseEntity.status(200).body(tarifService.add(tarifDto));
    }
    //READ
    @GetMapping
    public HttpEntity<?> getTariff(){
        return ResponseEntity.status(200).body(tarifService.get());
    }
    //UPDATE
    @PutMapping(value = "/{id}")
    public HttpEntity<?> editTarff(@Valid @PathVariable Integer id, @RequestBody TarifDto tarifDto ){
        return ResponseEntity.status(200).body(tarifService.edit(id,tarifDto));
    }
    //DELETE
    @DeleteMapping(value = "/{id}")
    public HttpEntity<?> deleteTariff(@PathVariable Integer id){
        return ResponseEntity.ok(tarifService.delete(id));
    }
    //READ BY ID
    @GetMapping(value = "/{id}")
    public HttpEntity<?> getByIdTariff(@PathVariable Integer id){
        return ResponseEntity.ok(tarifService.getById(id));
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
