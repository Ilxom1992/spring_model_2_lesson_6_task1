package com.example.demo.repository;

import com.example.demo.entity.SimCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SimCardRepository extends JpaRepository<SimCard,Integer> {
    Optional<SimCard> findByCodeAndNumber(String code, String number);
    Optional<SimCard> findByPhoneNumber(String phoneNumber);
}

