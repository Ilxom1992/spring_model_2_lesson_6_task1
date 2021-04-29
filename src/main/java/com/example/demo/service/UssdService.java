package com.example.demo.service;

import com.example.demo.config.GetTheUser;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.entity.Ussd;
import com.example.demo.entity.enams.RoleEnum;
import com.example.demo.payload.Response;
import com.example.demo.repository.UssdRepository;
import org.springframework.stereotype.Service;

@Service
public class UssdService {
    final UssdRepository ussdRepository;

    public UssdService(UssdRepository ussdRepository) {
        this.ussdRepository = ussdRepository;
    }
GetTheUser getTheUser=new GetTheUser();
    //  CREATE
    public Response add(Ussd ussd){
        User user = getTheUser.getCurrentAuditorUser().get();
        for (Role role:user.getRoles() ) {
            if (role.getId()==4){
                return new Response("object saved",true,ussdRepository.save(ussd));
            }
        }
        return new Response("Not add Ussd",true);
    }
    //READ
    public Response get(){
        return new Response("object ",true,ussdRepository.findAll());
    }
    //UPDATE
    public Response edit(Integer id,Ussd ussd){
        User user = getTheUser.getCurrentAuditorUser().get();
        for (Role role:user.getRoles() ) {
            if (role.getId()==4){
                Ussd ussd1=ussdRepository.findById(id).get();
                ussd1.setUssdCode(ussd.getUssdCode());
                ussd1.setDescription(ussd1.getDescription());
                ussdRepository.save(ussd1);
                return new Response("object edited",true);
            }
        }
        return new Response("Not add Ussd",true);
    }

}
