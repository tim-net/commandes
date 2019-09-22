package com.netisov.tim.commandes.controllers;

import com.netisov.tim.commandes.api.rest.OrdersController;
import com.netisov.tim.commandes.api.rest.representation.*;
import com.netisov.tim.commandes.domain.*;
import com.netisov.tim.commandes.repository.ArticleRepository;
import com.netisov.tim.commandes.repository.OrderRepository;
import com.netisov.tim.commandes.repository.OrderStateRepository;
import com.netisov.tim.commandes.service.OrderService;
import com.netisov.tim.commandes.service.OrderStateService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(value = OrdersController.class)
/**
 * Test for testing the orders REST controller class.
 */

public class OrdersControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private ArticleRepository articleRepository;
    @MockBean
    private OrderStateRepository orderStateRepository;

    private Order order1;
    @Before
    public void init() {
        OrderState initialState = OrderState.builder()
                .code("NV")
                .label("Nouvelle")
                .outcome(OrderOutcome.OPEN)
                .build();
        LocalDateTime created = LocalDateTime.of(2018, Month.OCTOBER, 1, 0, 0);



        Client client1 = Client.builder()
                .id(1L)
                .code("CL1")
                .name("Client 1")
                .build();
        order1 = Order.builder()
                .id(1L)
                .client(client1)
                .price(20.2)
                .shippingCountry(Country.builder()
                        .code("FR")
                        .label("France")
                        .build())
                .build();
        // As created field cannot be inserted or updated, we need to set it explicitly using reflection
        setInaccessibleField(order1, created, "createdAt");
        order1.setState(initialState);
        ArticleFamily family1 = ArticleFamily.builder()
                .code("VETE")
                .label("Vetements ete")
                .build();
        Article article1 = Article.builder()
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
        setInaccessibleField(orderLine1, 1L, "id");

        order1.setOrderLines(new ArrayList<>());
        order1.getOrderLines().add(orderLine1);
        given(orderRepository.getOne(1L)).willReturn(order1);
    }

    private void setInaccessibleField(Object object, Object value, String fieldName) {
        try {
            Field createdField = object.getClass().getDeclaredField(fieldName);
            createdField.setAccessible(true);
            createdField.set(object, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("There is a mistake in the test code");
        }
    }


    @Test
    public void getOneWithLinesTest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/api/order/1").accept(
                MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = "{'id':1,'createdAt':'2018-10-01T00:00:00','client':{'id':1,'code':'CL1','name':'Client 1'}," +
                "'shippingCountry':{'code':'FR','label':'France'},'state':{'code':'NV','label':'Nouvelle','outcome':'OPEN'}," +
                "'price':20.2,'lines':[{'id':1,'article':{'code':'TSH1','label':'T-shirt 1','family':{'code':'VETE','label':'Vetements ete'}," +
                "'price':10.1},'price':20.2,'amount':2}]}";
        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }
    @Configuration
    public static class LocalTestConfiguration {
        @Autowired
        private AutowireCapableBeanFactory factory;

        @Bean
        public OrderService orderService() {
            return factory.createBean(OrderService.class);
        }

        @Bean
        public OrdersController ordersController() {
            return factory.createBean(OrdersController.class);
        }

        @Bean
        public OrderStateService orderStateService() {
            return factory.createBean(OrderStateService.class);
        }
        @Bean
        public OrderConverter orderConverter() {
            return factory.createBean(OrderConverter.class);
        }
        @Bean
        public ClientConverter clientConverter() {
            return factory.createBean(ClientConverter.class);
        }
        @Bean
        public CountryConverter countryConverter() {
            return factory.createBean(CountryConverter.class);
        }

        @Bean
        public ArticleConverter articleConverter() {
            return factory.createBean(ArticleConverter.class);
        }
        @Bean
        public OrderStateConverter orderStateConverter() {
            return factory.createBean(OrderStateConverter.class);
        }

    }

}
