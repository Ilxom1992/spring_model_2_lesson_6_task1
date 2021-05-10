package com.example.demo.repository;

import ch.qos.logback.core.net.server.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;


public interface ClientRepository extends JpaRepository<Client,UUID> {
    Optional<com.example.demo.entity.Client> findByPassportNumber(String passportNumber);

    boolean existsByPassportNumber(String passportNumber);


}
