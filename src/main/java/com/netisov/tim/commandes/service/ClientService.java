package com.netisov.tim.commandes.service;

import com.netisov.tim.commandes.domain.Client;
import com.netisov.tim.commandes.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ClientService {

    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Client> getAll() {
        return repository.findAll();
    }
}
