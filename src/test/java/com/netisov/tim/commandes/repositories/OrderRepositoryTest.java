package com.netisov.tim.commandes.repositories;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.netisov.tim.commandes.domain.Order;
import com.netisov.tim.commandes.dto.order.OrderListFilter;
import com.netisov.tim.commandes.repository.OrderRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.*;

@DatabaseSetup("/data/repository/db-before.yml")
@DatabaseSetup("/data/repository/order/db-before.yml")
@DatabaseTearDown("/data/repository/db-teardown.yml")
public class OrderRepositoryTest extends RepositoryTestBase {
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void findOne(){
        Order order = orderRepository.getOne(1L);
        assertNotNull(order);
        assertEquals( Long.valueOf(1L),order.getId());
        assertEquals( Long.valueOf(1L),order.getClient().getId());
        assertEquals( "FR",order.getShippingCountry().getCode());
        assertEquals( "NV",order.getState().getCode());
    }

    @Test
    public void search() {
        OrderListFilter filter = new OrderListFilter();
        filter.setSort("-id");
        Page<Order> result = orderRepository.findAll(filter.predicate(), filter.pageable());
        assertNotNull(result);
        assertEquals(3L, result.getTotalElements());

        filter.setClientName("Pimkin Antibes");

        result = orderRepository.findAll(filter.predicate(), filter.pageable());
        assertNotNull(result);
        assertEquals(1L, result.getTotalElements());
        assertEquals(Long.valueOf(3L), result.getContent().get(0).getId());


        filter = new OrderListFilter();
        filter.setSort("-id");
        LocalDateTime created = LocalDateTime.of(2018, Month.DECEMBER, 1, 0, 0);
        filter.setFromCreatedAt(created);

        result = orderRepository.findAll(filter.predicate(), filter.pageable());
        assertNotNull(result);
        assertEquals(1L, result.getTotalElements());
        assertEquals(Long.valueOf(2L), result.getContent().get(0).getId());
    }


}
