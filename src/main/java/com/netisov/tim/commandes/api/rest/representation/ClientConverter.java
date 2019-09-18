package com.netisov.tim.commandes.api.rest.representation;

import com.netisov.tim.commandes.domain.Client;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ClientConverter implements Function<Client, ClientRepresentation> {
    @Override
    public ClientRepresentation apply(Client client) {
        return ClientRepresentation.builder()
                .id(client.getId())
                .code(client.getCode())
                .name(client.getName())
                .build();
    }
}
