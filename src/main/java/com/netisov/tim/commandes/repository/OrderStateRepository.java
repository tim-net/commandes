package com.netisov.tim.commandes.repository;

import com.netisov.tim.commandes.domain.OrderState;
import com.netisov.tim.commandes.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStateRepository extends BaseRepository<OrderState, String> {
}
