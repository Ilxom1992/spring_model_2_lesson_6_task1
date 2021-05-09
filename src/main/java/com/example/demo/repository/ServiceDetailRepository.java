package com.example.demo.repository;

import com.example.demo.entity.Detail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ServiceDetailRepository extends JpaRepository<Detail, Integer> {
}
