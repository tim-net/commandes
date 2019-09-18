package com.netisov.tim.commandes.repository;

import com.netisov.tim.commandes.domain.Country;
import com.netisov.tim.commandes.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends BaseRepository<Country, String> {
    @Query("select c from Country c order by c.label desc ")
    List<Country> findAllSortedByLabel();
}
