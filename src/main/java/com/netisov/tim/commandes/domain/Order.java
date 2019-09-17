package com.netisov.tim.commandes.domain;

import com.querydsl.core.annotations.QueryInit;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "order")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;

    @QueryInit("*")
    @ManyToOne
    @NotNull
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @QueryInit("*")
    @ManyToOne
    @NotNull
    @JoinColumn(name = "shipping_country_code", referencedColumnName = "code")
    private Country shippingCountry;

    @QueryInit("*")
    @ManyToOne
    @NotNull
    @Setter
    @JoinColumn(name = "state_code", referencedColumnName = "code")
    private OrderState state;


    @Setter
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderLine> orderLines;


    @Setter
    @Positive
    @Column(name = "price")
    private Double price;

    @Builder
    public Order(Client client, Country shippingCountry, OrderState state,  Double price) {
        Objects.requireNonNull(client);
        Objects.requireNonNull(shippingCountry);
        Objects.requireNonNull(state);
        Objects.requireNonNull(price);
        this.client = client;
        this.shippingCountry = shippingCountry;
        this.state = state;
        this.price = price;
    }

}
