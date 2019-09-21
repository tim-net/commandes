package com.netisov.tim.commandes.api.rest.representation;

import com.netisov.tim.commandes.domain.OrderState;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class OrderStateConverter implements Function<OrderState, OrderStateRepresentation> {
    @Override
    public OrderStateRepresentation apply(OrderState state) {
        return OrderStateRepresentation.builder()
                .code(state.getCode())
                .label(state.getLabel())
                .outcome(state.getOutcome().name())
                .build();
    }
}
