package com.netisov.tim.commandes.services;

import com.netisov.tim.commandes.api.rest.representation.OrderConverter;
import com.netisov.tim.commandes.domain.*;
import com.netisov.tim.commandes.repository.ArticleRepository;
import com.netisov.tim.commandes.repository.OrderRepository;
import com.netisov.tim.commandes.service.OrderService;
import com.netisov.tim.commandes.service.OrderStateService;
import com.sun.tools.javac.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {
    @Autowired
    private OrderService orderService;
    @MockBean
    private OrderStateService orderStateService;
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private ArticleRepository articleRepository;

    private Order order1;
    private Article article1;

    @Before
    public void init() {
        OrderState initialState = OrderState.builder()
                .code("NV")
                .label("Nouvelle")
                .outcome(OrderOutcome.OPEN)
                .build();
        Client client1 = Client.builder()
                .code("CL1")
                .name("Client 1")
                .build();
        order1 = Order.builder()
                .client(client1)
                .price(20.2)
                .shippingCountry(Country.builder()
                        .code("FR")
                        .label("France")
                        .build())
                .state(initialState)
                .build();

        ArticleFamily family1 = ArticleFamily.builder()
                .code("VETE")
                .label("Vetements ete")
                .build();
        article1 = Article.builder()
                .code("TSH1")
                .family(family1)
                .price(10.1)
                .label("T-shirt 1")
                .build();
        OrderLine orderLine1 = OrderLine.builder()
                .article(article1)
                .amount(2)
                .price(20.2)
                .order(order1)
                .build();
        order1.setOrderLines(new ArrayList<>());
        order1.getOrderLines().add(orderLine1);
        given(orderStateService.getInitialState()).willReturn(initialState);
        given(articleRepository.getOne("TSH1")).willReturn(article1);
        given(orderRepository.getOne(1L)).willReturn(order1);
        given(orderRepository.save(order1)).willReturn(order1);
    }

    @Test
    public void saveTest() {
        Order check = orderService.saveOrUpdate(order1);
        Assert.assertEquals(Double.valueOf(20.2), check.getPrice());
        Assert.assertEquals("CL1", check.getClient().getCode());
        Assert.assertEquals("FR", check.getShippingCountry().getCode());
        Assert.assertEquals("NV", check.getState().getCode());
        Assert.assertEquals(1, check.getOrderLines().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveIncorrectOrderLinePrice() {
        OrderLine wrong = OrderLine.builder()
                .article(article1)
                .amount(2)
                .price(1.2)
                .order(order1)
                .build();
        order1.getOrderLines().add(wrong);
        try {
            orderService.saveOrUpdate(order1);
        } finally {
            order1.getOrderLines().remove(wrong);
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveIncorrectTotal() {
        OrderLine another = OrderLine.builder()
                .article(article1)
                .amount(2)
                .price(20.2)
                .order(order1)
                .build();
        order1.getOrderLines().add(another);
        try {
            orderService.saveOrUpdate(order1);
        } finally {
            order1.getOrderLines().remove(another);
        }
    }

    @Test
    public void getOne() {
        Order check = orderService.getOne(1L);
        Assert.assertEquals(Double.valueOf(20.2), check.getPrice());
        Assert.assertEquals("CL1", check.getClient().getCode());
        Assert.assertEquals("FR", check.getShippingCountry().getCode());
        Assert.assertEquals("NV", check.getState().getCode());
        Assert.assertEquals(1, check.getOrderLines().size());
    }

}
