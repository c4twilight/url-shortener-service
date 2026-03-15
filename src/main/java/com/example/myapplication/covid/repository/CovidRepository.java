package com.example.myapplication.covid.repository;

import com.example.myapplication.covid.model.Covid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CovidRepository extends JpaRepository<Covid, Long> {
}
