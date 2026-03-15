package com.example.myapplication.covid.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "covid_stats")
public class Covid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "country is required")
    private String country;

    @Min(value = 0, message = "active must be >= 0")
    private Integer active;

    @Min(value = 0, message = "death must be >= 0")
    private Integer death;

    @Min(value = 0, message = "recovered must be >= 0")
    private Integer recovered;

    public Covid() {
    }

    public Covid(Long id, String country, Integer active, Integer death, Integer recovered) {
        this.id = id;
        this.country = country;
        this.active = active;
        this.death = death;
        this.recovered = recovered;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getDeath() {
        return death;
    }

    public void setDeath(Integer death) {
        this.death = death;
    }

    public Integer getRecovered() {
        return recovered;
    }

    public void setRecovered(Integer recovered) {
        this.recovered = recovered;
    }
}
