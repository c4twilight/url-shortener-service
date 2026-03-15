package com.example.myapplication.covid.service.impl;

import com.example.myapplication.covid.exception.BadRequestException;
import com.example.myapplication.covid.exception.ElementNotFoundException;
import com.example.myapplication.covid.model.Covid;
import com.example.myapplication.covid.repository.CovidRepository;
import com.example.myapplication.covid.service.CovidService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

@Service
public class DefaultCovidService implements CovidService {

    private final CovidRepository covidRepository;

    public DefaultCovidService(CovidRepository covidRepository) {
        this.covidRepository = covidRepository;
    }

    @Override
    public List<Covid> getAllCovid() {
        return covidRepository.findAll();
    }

    @Override
    public Covid createNewCovid(Covid covid) {
        if (covid.getId() != null) {
            throw new BadRequestException("The ID must not be provided when creating a new Covid record");
        }
        return covidRepository.save(covid);
    }

    @Override
    public Covid getCovidById(Long id) {
        return covidRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Covid entry not found for id=" + id));
    }

    @Override
    public List<Covid> top5By(String by) {
        validateBy(by);

        return covidRepository.findAll()
                .stream()
                .sorted(getComparator(by).reversed().thenComparing(Covid::getId))
                .limit(5)
                .toList();
    }

    @Override
    public Integer totalBy(String by) {
        validateBy(by);

        return covidRepository.findAll()
                .stream()
                .map(getFieldExtractor(by))
                .reduce(0, Integer::sum);
    }

    private void validateBy(String by) {
        if (!"active".equals(by) && !"death".equals(by) && !"recovered".equals(by)) {
            throw new BadRequestException("Invalid 'by' parameter. Allowed values: active, death, recovered");
        }
    }

    private Comparator<Covid> getComparator(String by) {
        return switch (by) {
            case "active" -> Comparator.comparing(Covid::getActive);
            case "death" -> Comparator.comparing(Covid::getDeath);
            case "recovered" -> Comparator.comparing(Covid::getRecovered);
            default -> throw new BadRequestException("Invalid 'by' parameter");
        };
    }

    private Function<Covid, Integer> getFieldExtractor(String by) {
        return switch (by) {
            case "active" -> Covid::getActive;
            case "death" -> Covid::getDeath;
            case "recovered" -> Covid::getRecovered;
            default -> throw new BadRequestException("Invalid 'by' parameter");
        };
    }
}
