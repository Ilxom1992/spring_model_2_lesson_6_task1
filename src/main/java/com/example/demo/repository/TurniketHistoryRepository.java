package com.example.demo.repository;

import com.example.demo.entity.TurniketHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TurniketHistoryRepository extends JpaRepository<TurniketHistory,Integer> {
}
