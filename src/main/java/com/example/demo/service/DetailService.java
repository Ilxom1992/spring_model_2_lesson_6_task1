package com.example.demo.service;

import com.example.demo.config.GetTheUser;
import com.example.demo.entity.Detail;
import com.example.demo.entity.Role;
import com.example.demo.entity.ServiceType;
import com.example.demo.entity.SimCard;
import com.example.demo.payload.ApiResponse;
import com.example.demo.payload.DetailDto;
import com.example.demo.payload.Response;

import com.example.demo.repository.DetailRepository;
import com.example.demo.repository.ServiceTypeRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;


@Service
public class DetailService {
    final DetailRepository detailRepository;
    final ServiceTypeRepository serviceTypeRepository;

    public DetailService(DetailRepository detailRepository, ServiceTypeRepository serviceTypeRepository) {
        this.detailRepository = detailRepository;
        this.serviceTypeRepository = serviceTypeRepository;
    }
    GetTheUser getTheUser=new GetTheUser();
    //  CREATE


        public Response addDetail(DetailDto detailDto) {
            Set<Role> roles = getTheUser.getCurrentAuditorUser().get().getRoles();
            for (Role role : roles) {
                if (role.getId() == 3) {
                    Detail detail = new Detail();
                    detail.setAmount(detailDto.getAmount());
                    Optional<ServiceType> optionalServiceType = serviceTypeRepository.findById(detailDto.getServiceTypeId());
                    if (!optionalServiceType.isPresent()) {
                        return new Response("Not found Service Type", false);
                    }
                    detail.setServiceType(optionalServiceType.get());
                    return new Response("object saved", true, detailRepository.save(detail));
                }
            }
         return   new Response("Not add ", false);
        }
        //READ
        public Response getDetail(){
            return new Response("object ",true, detailRepository.findAll());
        }
        //UPDATE

        public Response editDetail(Integer id,DetailDto detailDto){

            Set<Role> roles = getTheUser.getCurrentAuditorUser().get().getRoles();
            for (Role role : roles) {
                if (role.getId() == 3) {
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
        }}

            return   new Response("Not add ", false);
        }
        //DELETE
        public Response deleteDetail( Integer id ){
                Set<Role> roles = getTheUser.getCurrentAuditorUser().get().getRoles();
                for (Role role : roles)
                    if (role.getId() == 3) {


                 detailRepository.deleteById(id);
            return new Response("object deleted",true);
        }
            return   new Response("Not add ", false);
        }

        //READ BY ID
        public Response getByIdDetail( Integer id ){
            return new Response("object deleted",true,detailRepository.findById(id));
        }
    //details qo'shish
    public ApiResponse add(DetailDto detailDto){
        SimCard principal = (SimCard) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal.getId().toString().equals(detailDto.getSimCard().getId().toString())){
            Detail details = new Detail();
            details.setAmount(detailDto.getAmount());

            details.setSimCard(detailDto.getSimCard());
            detailRepository.save(details);
            return new ApiResponse("Details saqlandi!", true);
        }

        return new ApiResponse("Error!", false);
    }
    }
