package com.netisov.tim.commandes.api.rest.representation;

import com.netisov.tim.commandes.domain.*;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OrderRepresentationConverter implements Function<OrderRepresentation, Order> {
    @Override
    public Order apply(OrderRepresentation orderRepresentation) {
        Order order = Optional.of(orderRepresentation).map(o -> Order.builder()
                .client(Client.builder()
                        .id(o.getClient().getId())
                        .code(o.getClient().getCode())
                        .name(o.getClient().getName()).build())
                .shippingCountry(Country.builder()
                        .code(o.getShippingCountry().getCode())
                        .label(o.getShippingCountry().getLabel()).build())
                .state(OrderState.builder()
                        .code(o.getState().getCode())
                        .label(o.getState().getLabel())
                        .outcome(OrderOutcome.valueOf(o.getState().getOutcome())).build())
                .price(o.getPrice())
                .build()
        ).get();
        order.setOrderLines(orderRepresentation.getLines().stream().map(l -> OrderLine.builder()
                .amount(l.getAmount())
                .price(l.getPrice())
                .article(Article.builder()
                        .code(l.getArticle().getCode())
                        .family(ArticleFamily.builder()
                                .code(l.getArticle().getFamily().getCode())
                                .label(l.getArticle().getFamily().getLabel())
                                .build())
                        .label(l.getArticle().getLabel())
                        .price(l.getArticle().getPrice())
                        .build())
                .build()).collect(Collectors.toList()));
        return order;
    }
}
