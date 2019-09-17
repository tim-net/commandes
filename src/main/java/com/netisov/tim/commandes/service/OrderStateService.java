package com.netisov.tim.commandes.service;


import com.netisov.tim.commandes.domain.OrderState;
import com.netisov.tim.commandes.repository.OrderStateRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Validated
@Transactional
public class OrderStateService {

    private final OrderStateRepository repository;

    public OrderStateService(OrderStateRepository repository) {
        this.repository = repository;
    }

    public OrderState getInitialState() {
        Optional<OrderState> nv = repository.findById("NV");
        if (nv.isPresent()) {
            return nv.get();
        } else throw new IllegalStateException("System error in finding an initial state for an order");
    }
}
