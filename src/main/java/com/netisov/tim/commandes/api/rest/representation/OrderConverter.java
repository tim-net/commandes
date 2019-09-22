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
    private final OrderStateConverter orderStateConverter;

    public OrderConverter(ClientConverter clientConverter, CountryConverter countryConverter, ArticleConverter articleConverter, OrderStateConverter orderStateConverter) {
        this.clientConverter = clientConverter;
        this.countryConverter = countryConverter;
        this.articleConverter = articleConverter;
        this.orderStateConverter = orderStateConverter;
    }

    @Override
    public OrderRepresentation apply(Order order) {
        return OrderRepresentation.builder()
                .id(order.getId())
                .client(clientConverter.apply(order.getClient()))
                .createdAt(order.getCreatedAt())
                .price(order.getPrice())
                .shippingCountry(countryConverter.apply(order.getShippingCountry()))
                .state(orderStateConverter.apply(order.getState()))
                .lines(order.getOrderLines()
                        .stream()
                        .map(l -> OrderLineRepresentation.builder()
                                .id(l.getId())
                                .amount(l.getAmount())
                                .price(l.getPrice())
                                .article(articleConverter.apply(l.getArticle()))
                                .build()).collect(Collectors.toList()))
                .build();
    }
}
