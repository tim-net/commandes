package com.netisov.tim.commandes.api.rest;

import com.netisov.tim.commandes.api.rest.representation.*;
import com.netisov.tim.commandes.domain.Order;
import com.netisov.tim.commandes.dto.order.OrderListFilter;
import com.netisov.tim.commandes.service.OrderService;
import com.netisov.tim.commandes.service.OrderStateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Rest controller for operations with orders
 */
@RestController
@RequestMapping("/api/order")
public class OrdersController {

    private final OrderService orderService;
    private final OrderStateService orderStateService;
    private final OrderConverter orderConverter;
    private final OrderStateConverter orderStateConverter;
    private final OrderListConverter orderListConverter = new OrderListConverter();
    private final OrderRepresentationConverter orderRepresentationConverter = new OrderRepresentationConverter();
    private final Logger log = LoggerFactory.getLogger(OrdersController.class);

    public OrdersController(OrderService orderService, OrderStateService orderStateService, OrderConverter orderConverter, OrderStateConverter orderStateConverter) {
        this.orderService = orderService;
        this.orderStateService = orderStateService;
        this.orderConverter = orderConverter;
        this.orderStateConverter = orderStateConverter;
    }

    @GetMapping
    public OrderListRepresentation findAll(OrderListFilter filter) {
        log.debug("REST request to get a page of Orders");

        Page<OrderListRepresentation.OrderRepresentation> result = orderService.search(filter, orderListConverter);
        return OrderListRepresentation.builder()
                .count(result.getTotalElements())
                .orders(result.getContent())
                .build();
    }

    @GetMapping("/{id}")
    public OrderRepresentation getOne(@PathVariable Long id) {
        log.debug("REST request to get an order");
        return orderConverter.apply(orderService.getOne(id));
    }

    @PutMapping
    public OrderRepresentation saveOrUpdate(@RequestBody OrderRepresentation orderRepresentation) {
        log.debug("REST request to save or update an order");
        Order order = orderService.saveOrUpdate(orderRepresentationConverter.apply(orderRepresentation));
        return orderConverter.apply(order);
    }

    @GetMapping("/states")
    public List<OrderStateRepresentation> getStates() {
        log.debug("REST request to get order states");
        return orderStateService.getStates().stream().map(orderStateConverter).collect(Collectors.toList());
    }

}
