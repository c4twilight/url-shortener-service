package com.example.myapplication.covid.service;

import com.example.myapplication.covid.model.Covid;

import java.util.List;

public interface CovidService {
    List<Covid> getAllCovid();

    Covid createNewCovid(Covid covid);

    Covid getCovidById(Long id);

    List<Covid> top5By(String by);

    Integer totalBy(String by);
}
