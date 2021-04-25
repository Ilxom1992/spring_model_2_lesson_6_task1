package com.example.demo.service;

import com.example.demo.entity.Tarif;
import com.example.demo.payload.TarifDto;
import com.example.demo.repository.TarifRepository;
import org.springframework.stereotype.Service;
import com.example.demo.payload.Response;

import java.util.Optional;

@Service
public class TarifService {
    final TarifRepository tarifRepository;

    public TarifService(TarifRepository tarifRepository) {
        this.tarifRepository = tarifRepository;
    }

    //  CREATE
    public Response add(Tarif tarif){
        return new Response("object saved",true,tarifRepository.save(tarif));
    }
    //READ
    public Response get(){
        return new Response("object ",true,tarifRepository.findAll());
    }
    //UPDATE
    public Response edit(Integer id, TarifDto tarifDto){
        Optional<Tarif> optionalTarif = tarifRepository.findById(id);
        if (!optionalTarif.isPresent()){
            return  new Response("Not found Tarif",false);
        }
        Tarif tarif=optionalTarif.get();
        tarif.setTitle(tarifDto.getTitle());
        tarif.setDescription(tarifDto.getDescription());
        tarif.setPrice(tarif.getPrice());
        tarif.setStartDate(tarifDto.getStartDate());
        tarif.setEndDate(tarifDto.getEndDate());
        Tarif save = tarifRepository.save(tarif);
        return new Response("object edited",true,save);
    }
    //DELETE
    public Response delete( Integer id ){
        tarifRepository.deleteById(id);
        return new Response("object deleted",true);
    }
    //READ BY ID
    public Response getById( Integer id ){

        return new Response("object deleted",true,tarifRepository.findById(id));
    }

}
