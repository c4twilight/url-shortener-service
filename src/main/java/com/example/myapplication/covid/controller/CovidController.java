package com.example.myapplication.covid.controller;

import com.example.myapplication.covid.model.Covid;
import com.example.myapplication.covid.service.CovidService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/covid")
public class CovidController {

    private final CovidService covidService;

    public CovidController(CovidService covidService) {
        this.covidService = covidService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Covid> getAllCovid() {
        return covidService.getAllCovid();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Covid createCovid(@Valid @RequestBody Covid covid) {
        return covidService.createNewCovid(covid);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Covid getCovidById(@PathVariable Long id) {
        return covidService.getCovidById(id);
    }

    @GetMapping("/top5")
    @ResponseStatus(HttpStatus.OK)
    public List<Covid> top5By(@RequestParam("by") String by) {
        return covidService.top5By(by);
    }

    @GetMapping("/total")
    @ResponseStatus(HttpStatus.OK)
    public Integer totalBy(@RequestParam("by") String by) {
        return covidService.totalBy(by);
    }
}
