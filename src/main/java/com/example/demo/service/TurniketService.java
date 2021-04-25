package com.example.demo.service;

import com.example.demo.config.GetTheUser;
import com.example.demo.entity.Role;
//import com.example.demo.entity.Turniket;
import com.example.demo.entity.Turniket;
import com.example.demo.entity.TurniketHistory;
import com.example.demo.entity.User;
import com.example.demo.payload.Response;
import com.example.demo.repository.TurniketHistoryRepository;
import com.example.demo.repository.TurniketRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class TurniketService {
    final TurniketRepository turniketRepository;
    final TurniketHistoryRepository turniketHistoryRepository;
    final UserRepository userRepository;
    GetTheUser getTheUser=new GetTheUser();

    public TurniketService(TurniketRepository turniketRepository,TurniketHistoryRepository turniketHistoryRepository, UserRepository userRepository) {
        this.turniketRepository = turniketRepository;
        this.turniketHistoryRepository = turniketHistoryRepository;
        this.userRepository = userRepository;
    }

    public Response addHistory(Integer tuniketId) {

        // bazadan ushbu aydili turniketni oldik
        Optional<Turniket> optionalTourniquet = turniketRepository.findById(tuniketId);
        //bazada borligini tekshirdik bazada mavjud bo'lmasa qaytariladi
        if (!optionalTourniquet.isPresent())
        {return new Response("Invalid Tourniquet ID!", false);}

            boolean status = optionalTourniquet.get().isStatus();
            TurniketHistory turniketHistory=new TurniketHistory();
            turniketHistory.setTurniket(optionalTourniquet.get());
            turniketHistoryRepository.save(turniketHistory);
            Turniket turniket=optionalTourniquet.get();
        if (status){
               turniket.setStatus(false);
               turniketRepository.save(turniket);
                return new Response("User Exit ",true);}
            turniket.setStatus(true);
            turniketRepository.save(turniket);
            return new Response("User Enter ",true);
}
    public Response addturnket(String location, UUID userId) {
        Turniket turniket=new Turniket();
        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            return new Response("User not found",false);
        }
        Set<Role> roleSet = getTheUser.getCurrentAuditorUser().get().getRoles();
        for (Role role: roleSet) {
            //FAQAT DIRECTOR VA HR MANAGERGA TURNIKET BERISHGA RUHSAT ETILDI
           if (role.getId()==1 || role.getId()==2){
                turniket.setLocation(location);
                turniket.setUser(optionalUser.get());
                turniketRepository.save(turniket);
               return new Response(" add turniket",true);
            }
        }
        return new Response("Not add turniket",false);
    }
}
