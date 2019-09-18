package com.netisov.tim.commandes.service;

import com.netisov.tim.commandes.domain.Country;
import com.netisov.tim.commandes.repository.CountryRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CountryService {
    private final CountryRepository repository;

    public CountryService(CountryRepository repository) {
        this.repository = repository;
    }

    public List<Country> findAllSorted() {
        return repository.findAllSortedByLabel();
    }
}
