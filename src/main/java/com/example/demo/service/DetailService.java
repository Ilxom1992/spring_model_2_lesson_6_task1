package com.example.demo.service;

import com.example.demo.entity.Detail;
import com.example.demo.entity.ServiceType;
import com.example.demo.payload.DetailDto;
import com.example.demo.payload.Response;

import com.example.demo.repository.DetailRepository;
import com.example.demo.repository.ServiceTypeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class DetailService {
    final DetailRepository detailRepository;
    final ServiceTypeRepository serviceTypeRepository;

    public DetailService(DetailRepository detailRepository, ServiceTypeRepository serviceTypeRepository) {
        this.detailRepository = detailRepository;
        this.serviceTypeRepository = serviceTypeRepository;
    }
    //  CREATE
        public Response addDetail(DetailDto detailDto){
        Detail detail=new Detail();
        detail.setAmount(detailDto.getAmount());
            Optional<ServiceType> optionalServiceType = serviceTypeRepository.findById(detailDto.getServiceTypeId());
            if (!optionalServiceType.isPresent()){
                return new Response("Not found Service Type",false);
            }
            detail.setServiceType(optionalServiceType.get());
            return new Response("object saved",true, detailRepository.save(detail));
        }
        //READ
        public Response getDetail(){
            return new Response("object ",true, detailRepository.findAll());
        }
        //UPDATE
        public Response editDetail(Integer id,DetailDto detailDto){
            Optional<Detail> optionalDetail = detailRepository.findById(id);
            if (!optionalDetail.isPresent()){
                return new Response("Not found Detail",false);
            }
            Detail detail = optionalDetail.get();
            detail.setAmount(detailDto.getAmount());
            Optional<ServiceType> optionalServiceType = serviceTypeRepository.findById(detailDto.getServiceTypeId());
            if (!optionalServiceType.isPresent()){
                return new Response("Not found Service Type",false);
            }
            detail.setServiceType(optionalServiceType.get());
           detailRepository.save(detail);
            return new Response("object edited",true);
        }
        //DELETE
        public Response deleteDetail( Integer id ){
        detailRepository.deleteById(id);
            return new Response("object deleted",true);
        }
        //READ BY ID
        public Response getByIdDetail( Integer id ){
            return new Response("object deleted",true,detailRepository.findById(id));
        }
    }
