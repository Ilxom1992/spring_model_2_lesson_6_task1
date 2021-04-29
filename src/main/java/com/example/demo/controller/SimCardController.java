package com.example.demo.controller;

import com.example.demo.payload.Response;
import com.example.demo.payload.SimCardDto;
import com.example.demo.payload.SimCardServiceDto;
import com.example.demo.payload.SimCardTariffDto;
import com.example.demo.service.SimCardService;
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
import java.util.UUID;

@RestController
@RequestMapping("/simCard")
public class SimCardController {
    final SimCardService simCardService;

    public SimCardController(SimCardService simCardService) {
        this.simCardService = simCardService;
    }

    //  CREATE
    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody SimCardDto simCardDto){
        return ResponseEntity.status(200).body(simCardService.add(simCardDto));
    }
    //READ
    @GetMapping
    public HttpEntity<?> get(){
        return ResponseEntity.status(200).body(simCardService.get());
    }
    //DELETE
    @DeleteMapping(value = "/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        return ResponseEntity.ok(simCardService.delete(id));
    }
  @PostMapping("/registerSimCard")
    public HttpEntity<?>registerSimCard(@RequestParam String phoneNumber , @RequestParam Integer tarifId, @RequestParam UUID clientId){
      Response response = simCardService.registerSimCardToClient(phoneNumber, tarifId, clientId);
      return ResponseEntity.status(200).body(response);}


    @PostMapping("/service")
    public HttpEntity<?> connectServiceToSimCard(@RequestBody SimCardServiceDto simCardServiceDto) {
        Response response = simCardService.connectServiceToSimCard(simCardServiceDto);
        return ResponseEntity.status(response.isStatus() ? 201 : 401).body(response);
    }

    @PostMapping("/tariff")
    public HttpEntity<?> connectTariffToSimCard(@RequestBody SimCardTariffDto simCardTariffDto) {
        Response response = simCardService.connectTariffToSimCard(simCardTariffDto);
        return ResponseEntity.status(response.isStatus() ? 201 : 401).body(response);
    }


    @GetMapping("/tariff")
    public HttpEntity<?> findAllConnectedTariff() {
        return ResponseEntity.ok(simCardService.findAllConnectedTariff());
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
