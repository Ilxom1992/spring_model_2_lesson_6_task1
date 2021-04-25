package com.example.demo.controller;
import com.example.demo.payload.DetailDto;
import com.example.demo.service.DetailService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/detail")
public class DetailController {
    final DetailService detailService;

    public DetailController(DetailService detailService) {
        this.detailService = detailService;
    }
    //  CREATE
    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody DetailDto detailDto){
        return ResponseEntity.status(200).body(detailService.addDetail(detailDto));
    }
    //READ
    @GetMapping
    public HttpEntity<?> get(){
        return ResponseEntity.status(200).body(detailService.getDetail());
    }
    //UPDATE
    @PutMapping(value = "/{id}")
    public HttpEntity<?> edit(@Valid @RequestBody DetailDto detailDto,@PathVariable Integer id){
        return ResponseEntity.status(200).body(detailService.editDetail(id,detailDto));
    }
    //DELETE
    @DeleteMapping(value = "/{id}")
    public HttpEntity<?> delete(@PathVariable Integer id){
        return ResponseEntity.ok(detailService.deleteDetail(id));
    }
    //READ BY ID
    @GetMapping(value = "/{id}")
    public HttpEntity<?> getById(@PathVariable Integer id){
        return ResponseEntity.ok(detailService.getByIdDetail(id));
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
