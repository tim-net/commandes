package com.netisov.tim.commandes.api.rest.representation;

import com.netisov.tim.commandes.domain.Order;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OrderConverter implements Function<Order, OrderRepresentation> {
    private final ClientConverter clientConverter;
    private final CountryConverter countryConverter;
    private final ArticleConverter articleConverter;

    public OrderConverter(ClientConverter clientConverter, CountryConverter countryConverter, ArticleConverter articleConverter) {
        this.clientConverter = clientConverter;
        this.countryConverter = countryConverter;
        this.articleConverter = articleConverter;
    }

    @Override
    public OrderRepresentation apply(Order order) {
        return OrderRepresentation.builder()
                .client(clientConverter.apply(order.getClient()))
                .createdAt(order.getCreatedAt())
                .price(order.getPrice())
                .shippingCountry(countryConverter.apply(order.getShippingCountry()))
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
                                .article(articleConverter.apply(l.getArticle()))
                                .build()).collect(Collectors.toList()))
                .build();
    }
}
