package com.netisov.tim.commandes.api.rest;

import com.netisov.tim.commandes.api.rest.representation.CountryConverter;
import com.netisov.tim.commandes.api.rest.representation.CountryRepresentation;
import com.netisov.tim.commandes.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Rest controller for operations with countries
 */
@RestController
@RequestMapping("/api/country")
public class CountryController {
    private final Logger log = LoggerFactory.getLogger(CountryController.class);
    private final CountryConverter countryConverter;
    private final CountryService countryService;

    public CountryController(CountryConverter countryConverter, CountryService countryService) {
        this.countryConverter = countryConverter;
        this.countryService = countryService;
    }

    @GetMapping
    public List<CountryRepresentation> findAllSorted() {
        log.info("REST request to get all countries");
        return countryService.findAllSorted().stream().map(countryConverter).collect(Collectors.toList());
    }
}
