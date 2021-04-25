package com.example.demo.service;

import com.example.demo.entity.Ussd;
import com.example.demo.payload.Response;
import com.example.demo.repository.UssdRepository;
import org.springframework.stereotype.Service;

@Service
public class UssdService {
    final UssdRepository ussdRepository;

    public UssdService(UssdRepository ussdRepository) {
        this.ussdRepository = ussdRepository;
    }

    //  CREATE
    public Response add(Ussd ussd){
        return new Response("object saved",true,ussdRepository.save(ussd));
    }
    //READ
    public Response get(){

        return new Response("object ",true,ussdRepository.findAll());
    }
    //UPDATE
    public Response edit(Integer id,Ussd ussd){
        Ussd ussd1=ussdRepository.findById(id).get();
        ussd1.setUssdCode(ussd.getUssdCode());
        ussd1.setDescription(ussd1.getDescription());
        ussdRepository.save(ussd1);
        return new Response("object edited",true);
    }
    //DELETE
    public Response delete( Integer id ){
        ussdRepository.deleteById(id);
        return new Response("object deleted",true);
    }
    //READ BY ID
    public Response getById( Integer id ){

        return new Response("object deleted",true,ussdRepository.findById(id).get());
    }
}
