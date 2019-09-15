package com.netisov.tim.commandes.api.rest;

import com.netisov.tim.commandes.api.rest.representation.OrderListRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for operations with orders
 */
@RestController
@RequestMapping("/api/order")
public class OrdersController {

    private final Logger log = LoggerFactory.getLogger(OrdersController.class);

    @GetMapping
    public OrderListRepresentation findAll() {
return null;
    }

}
