package com.netisov.tim.commandes.api.rest;


import com.netisov.tim.commandes.api.rest.representation.ClientConverter;
import com.netisov.tim.commandes.api.rest.representation.ClientRepresentation;
import com.netisov.tim.commandes.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Rest controller for operations with clients
 */
@RestController
@RequestMapping("/api/client")
public class ClientController {
    private final Logger log = LoggerFactory.getLogger(ClientController.class);
    private final ClientConverter clientConverter;
    private final ClientService clientService;

    public ClientController(ClientConverter clientConverter, ClientService clientService) {
        this.clientConverter = clientConverter;
        this.clientService = clientService;
    }

    @GetMapping
    public List<ClientRepresentation> getAll() {
        log.info("REST request getting all clients");
        return clientService.getAll().stream().map(clientConverter).collect(Collectors.toList());
    }
}
