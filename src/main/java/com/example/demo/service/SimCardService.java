package com.example.demo.service;

import com.example.demo.config.GetTheUser;
import com.example.demo.entity.*;
import com.example.demo.entity.enams.RoleEnum;
import com.example.demo.payload.Response;
import com.example.demo.payload.SimCardDto;
import com.example.demo.payload.SimCardServiceDto;
import com.example.demo.payload.SimCardTariffDto;
import com.example.demo.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class SimCardService {
    final SimCardRepository simCardRepository;
    final UserRepository userRepository;
    final CompanyRepository companyRepository;
    final RoleRepository roleRepository;
    final TarifRepository tarifRepository;
    final ServiceRepository serviceRepository;
    final SimcardServiceRepository simcardServiceRepository;
    final SimCardTariffRepository  simCardTariffRepository;



    public SimCardService(SimCardRepository simCardRepository,
                          UserRepository userRepository, CompanyRepository companyRepository,
                          RoleRepository roleRepository, TarifRepository tarifRepository, ServiceRepository serviceRepository,
                          SimcardServiceRepository simcardServiceRepository, SimCardTariffRepository simCardTariffRepository) {
        this.simCardRepository = simCardRepository;
        this.userRepository = userRepository;

        this.companyRepository = companyRepository;
        this.roleRepository = roleRepository;
        this.tarifRepository = tarifRepository;
        this.serviceRepository = serviceRepository;
        this.simcardServiceRepository = simcardServiceRepository;


        this.simCardTariffRepository = simCardTariffRepository;
    }
GetTheUser getTheUser=new GetTheUser();
    //  CREATE
    public Response add(SimCardDto simCardDto){
        User user = getTheUser.getCurrentAuditorUser().get();
        for (Role role:user.getRoles()) {
            if (!role.getRoleName().equals(RoleEnum.NUMBER_MANAGER)){
                return new Response("Number Not add",false);
            }
            SimCard simCard=new SimCard();
            /**
             * Companiyaninig raqamlar bilan ishlovchi manager tizimga raqam qo'shishi mumkin
             * va raqam companiya codi va +998 bilan boshlangan holda tizimda saqlanadi
             * manager faqat 7 talik raqamni yuboradi yoki gineratsiya qilingan holda kelgan raqamlarni
             * bazaga saqlab qo'yiladi
             * faqat qo'shish raqamlar mangeriga ruhsat beriladi qo'shilgan
             * raqamlar bazaga saqlangach yuridik yoki jismoniy shaxga
             * biriktirib beriladi
             */
           String phoneNumber="+998"+user.getCompany().getSimCode()+simCardDto.getPhoneNumber();
           simCard.setPhoneNumber(phoneNumber);
           simCardRepository.save(simCard);
        }

        return new Response("object saved",true);
    }
    // READ

    /**
     * bazadagi simcartalar ro'yhatini ko'rish uchun
     * @return
     */
    public Response get(){
        return new Response("object ",true,simCardRepository.findAll());}
    //DELETE
    public Response delete( Integer id ){
        simCardRepository.deleteById(id);
        return new Response("object deleted",true);
    }
    public Response registerSimCardToClient(String phoneNumber, Integer tarifId, UUID clientId) {
        User users = getTheUser.getCurrentAuditorUser().get();
        for (Role role : users.getRoles()) {
            if (!role.getRoleName().equals(RoleEnum.NUMBER_MANAGER)) {
                return new Response("Number Not add", false);
            }
            /**
             * Clientga simcartani birictirish uchun oldin bazaga client ni saqlab oladi
             * client topilsa simcartaga clientni berib yuboradi
             */
            SimCard simCard = simCardRepository.findByPhoneNumber(phoneNumber);
            Optional<User> optionalUser = userRepository.findById(clientId);
            if (!optionalUser.isPresent())
                return new Response("Client Not add", false);
            simCard.setUser(optionalUser.get());
            simCard.setTarif(tarifRepository.findById(tarifId).get());
        }
        return new Response("SimCard Userga Biriktirildi",true);
    }

    //hizmatni simcartaga ulash
    public Response connectServiceToSimCard(SimCardServiceDto simCardServiceDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().equals("anonymousUser")) {
            User userContext = (User) authentication.getPrincipal();
            Set<Role> roles = userContext.getRoles();

            for (Role role : roles) {
                if (role.getRoleName().equals(RoleEnum.NUMBER_MANAGER) || role.getRoleName().equals(RoleEnum.CLIENT)) {
                    com.example.demo.entity.SimCardService simCardService=new com.example.demo.entity.SimCardService();
                    Optional<SimCard> optionalSimCard = simCardRepository.findById(simCardServiceDto.getSimCardId());
                    if(!optionalSimCard.isPresent())
                        return new Response("SimCard id was not found!", false);
                    simCardService.setSimcard(optionalSimCard.get());


                    Optional<com.example.demo.entity.Service> optionalService = serviceRepository.findById(simCardServiceDto.getServiceId());
                    if(!optionalService.isPresent())
                        return new Response("Service id was not found!", false);
                    simCardService.setService(optionalService.get());

                    // xarid qilingan xizmat narxi tekshirilib, hisobdan mablag' yechilishi
                    double service_price = optionalService.get().getPrice();
                    double balance = optionalSimCard.get().getBalance();
                    if(service_price >= balance) {
                        double newBalance = balance - service_price;
                        optionalSimCard.get().setBalance(newBalance);
                        simCardRepository.save(optionalSimCard.get());
                    } else {
                        return new Response("You don't have enough balance for buy this service!", false);
                    }


                    simcardServiceRepository.save(simCardService);
                    return new Response("To " + optionalSimCard.get().getPhoneNumber() + " connected " , true);
                }
            }
        }
        return new Response("Authorization empty!", false);
    }
    //Tarifni SimCard-ga ulang

    public Response connectTariffToSimCard(SimCardTariffDto simCardTariffDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!authentication.getPrincipal().equals("anonymousUser")) {
            User userContext = (User) authentication.getPrincipal();
            Set<Role> roles = userContext.getRoles();

            for (Role role : roles) {
                if (role.getRoleName().equals(RoleEnum.NUMBER_MANAGER) || role.getRoleName().equals(RoleEnum.CLIENT)) {
                    SimCardTariff simCardTariff = new SimCardTariff();
                    Optional<SimCard> optionalSimCard = simCardRepository.findById(simCardTariffDto.getSimCardId());
                    if(!optionalSimCard.isPresent())
                        return new Response("SimCard id was not found!", false);
                    simCardTariff.setSimCard(optionalSimCard.get());

                    Optional<Tarif> optionalTariff= tarifRepository.findById(simCardTariffDto.getTariffId());
                    if(!optionalTariff.isPresent())
                        return new Response("Tariff id was not found!", false);
                    simCardTariff.setTariff(optionalTariff.get());

                    // xarid qilingan tariff narxi tekshirilib, hisobdan mablag' yechilishi
                    double tariff_price = optionalTariff.get().getPrice();
                    double balance = optionalSimCard.get().getBalance();
                    if(tariff_price >= balance) {
                        double newBalance = balance - tariff_price;
                        optionalSimCard.get().setBalance(newBalance);
                        simCardRepository.save(optionalSimCard.get());
                    } else {
                        return new Response("You don't have enough balance for buy this tariff!", false);
                    }

                   simCardTariffRepository.save(simCardTariff);
                    return new Response("To " + optionalSimCard.get().getPhoneNumber() + " connected " + optionalTariff.get().getTitle(), true);
                }
            }
        }
        return new Response("Authorization empty!", false);
    }

    public List<SimCardTariff> findAllConnectedTariff() {
        return simCardTariffRepository.findAll();
    }

}
