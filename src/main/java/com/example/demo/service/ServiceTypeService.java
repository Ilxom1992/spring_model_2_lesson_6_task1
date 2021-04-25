package com.example.demo.service;

import com.example.demo.entity.ServiceType;
import com.example.demo.payload.Response;
import com.example.demo.payload.ServiceTypeDto;
import com.example.demo.repository.ServiceTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class ServiceTypeService {
    final ServiceTypeRepository serviceTypeRepository;

    public ServiceTypeService(ServiceTypeRepository serviceTypeRepository) {
        this.serviceTypeRepository = serviceTypeRepository;
    }
    //  CREATE
    public Response addServiceType(ServiceType serviceType){
        serviceTypeRepository.save(serviceType);
        return new Response("object saved",true);
    }
    //READ
    public Response getServiceType( ){
        return new Response("object ",true,serviceTypeRepository.findAll());
    }
    //UPDATE
    public Response editServiceType(Integer id, ServiceTypeDto serviceTypeDto ){
ServiceType serviceType=serviceTypeRepository.findById(id).get();
serviceType.setName(serviceTypeDto.getName());
serviceType.setDescription(serviceTypeDto.getDescription());
        ServiceType save = serviceTypeRepository.save(serviceType);
        return new Response("object edited",true,save);
    }
    //DELETE
    public Response deleteServiceType( Integer id ){
        serviceTypeRepository.deleteById(id);
        return new Response("object deleted",true);
    }
    //READ BY ID
    public Response getByIdServiceType( Integer id ){
        return new Response("object deleted",true,serviceTypeRepository.findById(id).get());
    }


}
