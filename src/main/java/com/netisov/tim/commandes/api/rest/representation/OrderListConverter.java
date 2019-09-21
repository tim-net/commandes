package com.netisov.tim.commandes.api.rest.representation;

import com.netisov.tim.commandes.domain.Order;

import java.util.function.Function;

public class OrderListConverter implements Function<Order, OrderListRepresentation.OrderRepresentation> {
    @Override
    public OrderListRepresentation.OrderRepresentation apply(Order order) {
        return OrderListRepresentation.OrderRepresentation.builder()
                .id(order.getId())
                .createdAt(order.getCreatedAt())
                .state(order.getState().getLabel())
                .client(order.getClient().getName())
                .shippingCountry(order.getShippingCountry().getLabel())
                .price(order.getPrice())
                .build();
    }
}
