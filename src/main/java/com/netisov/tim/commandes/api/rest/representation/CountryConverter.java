package com.netisov.tim.commandes.api.rest.representation;

import com.netisov.tim.commandes.domain.Country;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CountryConverter implements Function<Country, CountryRepresentation> {
    @Override
    public CountryRepresentation apply(Country country) {
        return CountryRepresentation.builder()
                .code(country.getCode())
                .label(country.getLabel())
                .build();
    }
}
