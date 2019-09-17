package com.netisov.tim.commandes.repository;

import com.netisov.tim.commandes.domain.Order;
import com.netisov.tim.commandes.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository (DB-access) for order entities
 */
@Repository
public interface OrderRepository extends BaseRepository<Order, Long> {
}
