package com.netisov.tim.commandes.repository;

import com.netisov.tim.commandes.domain.Client;
import com.netisov.tim.commandes.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends BaseRepository<Client, Long> {
}
