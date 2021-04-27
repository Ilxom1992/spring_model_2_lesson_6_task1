package com.example.demo.service;

import com.example.demo.entity.ServiceType;
import com.example.demo.entity.Ussd;
import com.example.demo.payload.Response;
import com.example.demo.payload.ServiceDto;
import com.example.demo.repository.ServiceRepository;
import com.example.demo.repository.ServiceTypeRepository;
import com.example.demo.repository.UssdRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class Services {
    final ServiceRepository serviceRepository;
    final ServiceTypeRepository serviceTypeRepository;
    final UssdRepository ussdRepository;

    public Services(ServiceRepository serviceRepository, ServiceTypeRepository serviceTypeRepository, UssdRepository ussdRepository) {
        this.serviceRepository = serviceRepository;
        this.serviceTypeRepository = serviceTypeRepository;
        this.ussdRepository = ussdRepository;
    }

    //  CREATE
    public Response add(ServiceDto serviceDto){
        com.example.demo.entity.Service service=new com.example.demo.entity.Service();
        service.setServiceName(serviceDto.getServiceName());
        service.setUssdCode(serviceDto.getUssdCode());
        service.setDescription(service.getDescription());
        Optional<ServiceType> optionalServiceType = serviceTypeRepository.findById(serviceDto.getServiceTypeId());
        if (!optionalServiceType.isPresent())
            return new Response("Not found ServiceType",false);
        service.setServiceType(optionalServiceType.get());
        Set set=new HashSet();
        for (Integer n:serviceDto.getUssdId()) {
            Optional<Ussd> optionalUssd = ussdRepository.findById(n);
            if (!optionalUssd.isPresent())
               return new Response("Not found Ussd code",false);
            set.add(optionalUssd.get());
        }
        service.setUssdSet(set);
        serviceRepository.save(service);
        return new Response("object saved",true);
    }
    //READ
    public Response get(){

        return new Response("object ",true, serviceRepository.findAll());}
    //UPDATE
    public Response edit(Integer id, ServiceDto serviceDto){
        Optional<com.example.demo.entity.Service> optionalService = serviceRepository.findById(id);
        if (!optionalService.isPresent())
          return new Response("Not found Services",false);
        com.example.demo.entity.Service service= optionalService.get();
        service.setServiceName(serviceDto.getServiceName());
        service.setUssdCode(serviceDto.getUssdCode());
        service.setDescription(service.getDescription());
        Optional<ServiceType> optionalServiceType = serviceTypeRepository.findById(serviceDto.getServiceTypeId());
        if (!optionalServiceType.isPresent())
            return new Response("Not found ServiceType",false);
        service.setServiceType(optionalServiceType.get());
        Set set=new HashSet();
        for (Integer n:serviceDto.getUssdId()) {
            Optional<Ussd> optionalUssd = ussdRepository.findById(n);
            if (!optionalUssd.isPresent())
                return new Response("Not found Ussd code",false);
            set.add(optionalUssd.get());
        }
        service.setUssdSet(set);
        serviceRepository.save(service);
        return new Response("object edited",true);
    }
    //DELETE
    public Response delete( Integer id ){
        Optional<com.example.demo.entity.Service> optionalService = serviceRepository.findById(id);
        if (!optionalService.isPresent())
            return new Response("Not found service",false);
        serviceRepository.deleteById(id);
        return new Response("object deleted",true);
    }
    //READ BY ID
    public Response getById( Integer id ){
        return new Response("object deleted",true,serviceRepository.findById(id));
    }

}
