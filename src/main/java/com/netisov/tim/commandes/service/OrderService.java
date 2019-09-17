package com.netisov.tim.commandes.service;

import com.netisov.tim.commandes.domain.Article;
import com.netisov.tim.commandes.domain.Order;
import com.netisov.tim.commandes.domain.OrderLine;
import com.netisov.tim.commandes.dto.PagingAndSortingRequest;
import com.netisov.tim.commandes.repository.ArticleRepository;
import com.netisov.tim.commandes.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.function.Function;

@Service
@Validated
@Transactional
public class OrderService {

    private final OrderRepository repository;
    private final ArticleRepository articleRepository;
    private final OrderStateService orderStateService;

    public OrderService(OrderRepository repository, ArticleRepository articleRepository, OrderStateService orderStateService) {
        this.repository = repository;
        this.articleRepository = articleRepository;
        this.orderStateService = orderStateService;
    }

    public <R> Page<R> search(@NotNull @Valid PagingAndSortingRequest<Order> filter, @NotNull Function<Order, R> converter) {
        return repository.findAll(filter.predicate(), filter.pageable()).map(converter);
    }

    public Order saveOrUpdate(Order order) {
        if (order.getId() != null) {
            Order original = repository.getOne(order.getId());
            // client cannot change the state of an order using this method
            order.setState(original.getState());
        } else {
            order.setState(orderStateService.getInitialState());
        }
        order.getOrderLines().forEach(l -> {
            Article article = articleRepository.getOne(l.getArticle().getCode());
            double price = BigDecimal.valueOf(article.getPrice() * l.getAmount())
                    .setScale(2, RoundingMode.HALF_UP)
                    .doubleValue();
            if (l.getPrice() != price) {
                throw new IllegalArgumentException("Illegal value of price in the order line");
            }
            l.setArticle(article);
        });
        double total = order.getOrderLines().stream().mapToDouble(OrderLine::getPrice).sum();
        if (order.getPrice() != total) {
            throw new IllegalArgumentException("Illegal value of price in the order");
        }

        return repository.save(order);
    }

    public Order getOne(Long id) {
        return repository.getOne(id);
    }
}
