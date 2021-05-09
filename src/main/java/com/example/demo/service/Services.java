package com.example.demo.service;

import com.example.demo.config.GetTheUser;
import com.example.demo.entity.*;
import com.example.demo.entity.enams.RoleEnum;
import com.example.demo.payload.DetailDto;
import com.example.demo.payload.Response;
import com.example.demo.payload.ServiceDto;
import com.example.demo.payload.TarifDto;
import com.example.demo.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class Services {
    final ServiceRepository serviceRepository;
    final ServiceTypeRepository serviceTypeRepository;
    final UssdRepository ussdRepository;
    final TarifRepository tarifRepository;
    final ServiceDetailRepository serviceDetailRepository;

    public Services(ServiceRepository serviceRepository, ServiceTypeRepository serviceTypeRepository, UssdRepository ussdRepository, TarifRepository tarifRepository, ServiceDetailRepository serviceDetailRepository) {
        this.serviceRepository = serviceRepository;
        this.serviceTypeRepository = serviceTypeRepository;
        this.ussdRepository = ussdRepository;
        this.tarifRepository = tarifRepository;
        this.serviceDetailRepository = serviceDetailRepository;
    }

    GetTheUser getTheUser=new GetTheUser();

    //  CREATE

    public Response getById( Integer id ){
        return new Response("object deleted",true,serviceRepository.findById(id));
    }

    public Response saveService(ServiceDto serviceDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().equals("anonymousUser")) {
            User userContext = (User) authentication.getPrincipal();
            Set<Role> roles = userContext.getRoles();

            for (Role role : roles) {
                if (role.getRoleName().equals(RoleEnum.TARIFF_MANAGER)) {

                    Optional<Detail> optionalDetail = serviceDetailRepository.findById(serviceDto.getDetailId());
                    if (!optionalDetail.isPresent())
                        return new Response("Detail id was not found!", false);

                    if (serviceRepository.existsByUssd(serviceDto.getUssdCode()))
                        return new Response("Such USSD code already exists!", false);

                    com.example.demo.entity.Service service = new com.example.demo.entity.Service();

                    service.setDescription(serviceDto.getDescription());
                    service.setPrice(serviceDto.getPrice());


                    service.setUssdCode(serviceDto.getUssdCode());
                    serviceRepository.save(service);
                    return new Response("New Service was successfully created!", false);
                }
            }
        }
        return new Response("Authorization empty!", false);
    }


    public Response saveTariff(TarifDto tariffDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().equals("anonymousUser")) {
            User userContext = (User) authentication.getPrincipal();
            Set<Role> roles = userContext.getRoles();

            for (Role role : roles) {
                if (role.getRoleName().equals(RoleEnum.TARIFF_MANAGER)) {

                    if (tarifRepository.existsByUssd(tariffDto.getUssd()))
                        return new Response("Such USSD code already exists!", false);

                    Tarif tariff = new Tarif();
                    tariff.setTitle(tariffDto.getTitle());
                    tariff.setDescription(tariffDto.getDescription());
                    tariff.setPrice(tariffDto.getPrice());
                    tariff.setCountDateOfExpire(tariffDto.getCountDateOfExpire());

                    Set<Integer> detailIdSet = tariffDto.getDetailIdSet();
                    Set<Detail> detailSet = new HashSet<>();
                    for (Integer detailId : detailIdSet) {
                        Optional<Detail> optionalDetail = serviceDetailRepository.findById(detailId);
                        optionalDetail.ifPresent(detailSet::add);
                    }
                    tariff.setDetails(detailSet);
                    tariff.setUssd(tariffDto.getUssd());
                    tarifRepository.save(tariff);
                    return new Response("New Tariff was successfully created!", false);
                }
            }
        }
        return new Response("Authorization empty!", false);
    }


    public Response saveServiceType(ServiceType serviceType) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().equals("anonymousUser")) {
            User userContext = (User) authentication.getPrincipal();
            Set<Role> roles = userContext.getRoles();

            for (Role role : roles) {
                if (role.getRoleName().equals(RoleEnum.TARIFF_MANAGER)) {
                    serviceTypeRepository.save(serviceType);
                    return new Response("Service Type saved!", false);
                }
            }
        }
        return new Response("Authorization empty!", false);

    }


    public Response editServiceType(Integer serviceTypeId, ServiceType serviceType) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().equals("anonymousUser")) {
            User userContext = (User) authentication.getPrincipal();
            Set<Role> roles = userContext.getRoles();

            for (Role role : roles) {
                if (role.getRoleName().equals(RoleEnum.TARIFF_MANAGER)) {

                    Optional<ServiceType> optionalServiceType = serviceTypeRepository.findById(serviceTypeId);
                    if (!optionalServiceType.isPresent())
                        return new Response("Service Type id was not found!", false);

                    optionalServiceType.get().setName(serviceType.getName());
                    optionalServiceType.get().setDescription(serviceType.getDescription());
                    serviceTypeRepository.save(optionalServiceType.get());

                    return new Response("Service Type updated!", false);
                }
            }
        }
        return new Response("Authorization empty!", false);

    }


    public Response deleteServiceType(Integer serviceTypeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().equals("anonymousUser")) {
            User userContext = (User) authentication.getPrincipal();
            Set<Role> roles = userContext.getRoles();

            for (Role role : roles) {
                if (role.getRoleName().equals(RoleEnum.TARIFF_MANAGER)) {
                    Optional<ServiceType> optionalServiceType = serviceTypeRepository.findById(serviceTypeId);
                    if (!optionalServiceType.isPresent())
                        return new Response("Service Type id was not found!", false);

                    serviceTypeRepository.deleteById(serviceTypeId);
                    return new Response("Service Type deleted!", false);
                }
            }
        }
        return new Response("Authorization empty!", false);
    }


    public Response saveServiceDetail(DetailDto detailDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().equals("anonymousUser")) {
            User userContext = (User) authentication.getPrincipal();
            Set<Role> roles = userContext.getRoles();

            for (Role role : roles) {
                if (role.getRoleName().equals(RoleEnum.TARIFF_MANAGER)) {
                    Detail detail = new Detail();
                    detail.setAmount(detailDto.getAmount());

                    Optional<ServiceType> optionalServiceType = serviceTypeRepository.findById(detailDto.getServiceTypeId());
                    if (!optionalServiceType.isPresent())
                        return new Response("Service Type id was not found!", false);

                    detail.setServiceType(optionalServiceType.get());
                    serviceDetailRepository.save(detail);
                    return new Response("Service Detail saved!", true);
                }
            }
        }
        return new Response("Authorization empty!", false);

    }
}
