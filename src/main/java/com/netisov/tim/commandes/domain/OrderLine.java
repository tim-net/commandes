package com.netisov.tim.commandes.domain;

import com.querydsl.core.annotations.QueryInit;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Objects;

@Entity
@Table(name = "order_line")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @QueryInit("*")
    @NotNull
    @ManyToOne
    @JoinColumn(name = "article_code", referencedColumnName = "code")
    private Article article;

    @Positive
    @Column(name = "amount")
    private Integer amount;

    @Positive
    @Column(name = "price")
    private Double price;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private OrderItem order;

    @Builder
    public OrderLine(Article article, Integer amount, Double price, OrderItem order) {
        Objects.requireNonNull(article);
        Objects.requireNonNull(amount);
        Objects.requireNonNull(price);
        Objects.requireNonNull(order);
        this.article = article;
        this.amount = amount;
        this.price = price;
        this.order = order;
    }

}
