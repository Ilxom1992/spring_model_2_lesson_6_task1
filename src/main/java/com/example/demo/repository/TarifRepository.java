package com.example.demo.repository;

import com.example.demo.entity.Tarif;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarifRepository extends JpaRepository<Tarif,Integer> {
    boolean existsByUssd(String ussd);
}
