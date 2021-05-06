package com.example.demo.service;

import com.example.demo.config.GetTheUser;
import com.example.demo.entity.Detail;
import com.example.demo.entity.Role;
import com.example.demo.entity.Tarif;
import com.example.demo.entity.Ussd;
import com.example.demo.payload.TarifDto;
import com.example.demo.repository.DetailRepository;
import com.example.demo.repository.TarifRepository;
import com.example.demo.repository.UssdRepository;
import org.springframework.stereotype.Service;
import com.example.demo.payload.Response;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class TarifService {
    final TarifRepository tarifRepository;
    final DetailRepository detailRepository;
    final UssdRepository ussdRepository;

    public TarifService(TarifRepository tarifRepository, DetailRepository detailRepository, UssdRepository ussdRepository) {
        this.tarifRepository = tarifRepository;
        this.detailRepository = detailRepository;
        this.ussdRepository = ussdRepository;
    }
    GetTheUser getTheUser=new GetTheUser();

    //  CREATE

    public Response add(TarifDto tarifDto){
        Set<Role> roles = getTheUser.getCurrentAuditorUser().get().getRoles();
        for (Role role : roles)
            if (role.getId() == 3 ||role.getId()==2) {
                Tarif tarif=new Tarif();
                tarif.setPrice(tarifDto.getPrice());
                tarif.setDescription(tarifDto.getDescription());
                tarif.setTitle(tarifDto.getTitle());
                tarif.setStartDate(tarifDto.getStartDate());
                tarif.setEndDate(tarifDto.getEndDate());
                Set<Detail> details=new HashSet<>();
                Set<Ussd> ussds=new HashSet<>();
                for (Integer detailId:tarifDto.getTarifDetailsId()) {
                    Optional<Detail> optionalDetail = detailRepository.findById(detailId);
                    if (!optionalDetail.isPresent())
                        return new Response("Not found Detils",false);
                    details.add(optionalDetail.get());
                }
                for (Integer ussdId:tarifDto.getTarifDetailsId()) {
                    Optional<Ussd> optionalUssd = ussdRepository.findById(ussdId);
                    if (!optionalUssd.isPresent())
                        return new  Response("Not found Ussd Code",false);
                    ussds.add(optionalUssd.get());
                }
                tarif.setDetails(details);
                tarif.setUssdSet(ussds);
                tarifRepository.save(tarif);
                return new Response("object saved",true,tarifRepository.save(tarif));
            } return   new Response("Not add ", false);


    }
    //READ
    public Response get(){
        return new Response("object ",true,tarifRepository.findAll());
    }
    //UPDATE
    public Response edit(Integer id, TarifDto tarifDto){
        Set<Role> roles = getTheUser.getCurrentAuditorUser().get().getRoles();
        for (Role role : roles)
            if (role.getId() == 3 ||role.getId()==2) {

        Optional<Tarif> optionalTarif = tarifRepository.findById(id);
        if (!optionalTarif.isPresent()){
            return  new Response("Not found Tarif",false);
        }
        Tarif tarif = optionalTarif.get();
        tarif.setPrice(tarifDto.getPrice());
        tarif.setDescription(tarifDto.getDescription());
        tarif.setTitle(tarifDto.getTitle());
        tarif.setStartDate(tarifDto.getStartDate());
        tarif.setEndDate(tarifDto.getEndDate());
        Set<Detail> details=new HashSet<>();
        Set<Ussd> ussds=new HashSet<>();
        for (Integer detailId:tarifDto.getTarifDetailsId()) {
            Optional<Detail> optionalDetail = detailRepository.findById(detailId);
            if (!optionalDetail.isPresent())
                return new Response("Not found Detils",false);
            details.add(optionalDetail.get());
        }
        for (Integer ussdId:tarifDto.getTarifDetailsId()) {
            Optional<Ussd> optionalUssd = ussdRepository.findById(ussdId);
            if (!optionalUssd.isPresent())
                return new  Response("Not found Ussd Code",false);
            ussds.add(optionalUssd.get());
        }
        tarif.setDetails(details);
        tarif.setUssdSet(ussds);
        tarifRepository.save(tarif);
        return new Response("object saved",true,tarifRepository.save(tarif));

    }return   new Response("Not add ", false);}
    //DELETE
    public Response delete( Integer id ){

        tarifRepository.deleteById(id);
        return new Response("object deleted",true);
    }
    //READ BY ID
    public Response getById( Integer id ){
        Set<Role> roles = getTheUser.getCurrentAuditorUser().get().getRoles();
        for (Role role : roles)
            if (role.getId() == 3 ||role.getId()==2) {
        return new Response("object deleted",true,tarifRepository.findById(id));
    }
        return   new Response("Not add ", false);}
}
