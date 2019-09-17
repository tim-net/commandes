package com.netisov.tim.commandes.api.rest.representation;

import com.netisov.tim.commandes.domain.Order;

import java.util.function.Function;
import java.util.stream.Collectors;

public class OrderConverter implements Function<Order, OrderRepresentation> {
    @Override
    public OrderRepresentation apply(Order order) {
        return OrderRepresentation.builder()
                .client(ClientRepresentation.builder()
                        .id(order.getClient().getId())
                        .code(order.getClient().getCode())
                        .name(order.getClient().getName())
                        .build())
                .createdAt(order.getCreatedAt())
                .price(order.getPrice())
                .shippingCountry(CountryRepresentation.builder()
                        .code(order.getShippingCountry().getCode())
                        .label(order.getShippingCountry().getLabel())
                        .build())
                .state(OrderStateRepresentation.builder()
                        .code(order.getState().getCode())
                        .label(order.getState().getLabel())
                        .outcome(order.getState().getOutcome().name())
                        .build())
                .lines(order.getOrderLines()
                        .stream()
                        .map(l -> OrderLineRepresentation.builder()
                                .amount(l.getAmount())
                                .price(l.getPrice())
                                .article(ArticleRepresentation.builder()
                                        .code(l.getArticle().getCode())
                                        .family(ArticleFamilyRepresentation.builder()
                                                .code(l.getArticle().getFamily().getCode())
                                                .label(l.getArticle().getFamily().getLabel())
                                                .build())
                                        .build())
                                .build()).collect(Collectors.toList()))
                .build();
    }
}
