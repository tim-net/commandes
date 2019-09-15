package com.netisov.tim.commandes.domain;

import com.querydsl.core.annotations.QueryInit;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "order")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotNull
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @QueryInit("*")
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @QueryInit("*")
    @ManyToOne
    @JoinColumn(name = "shipping_country_code", referencedColumnName = "code")
    private Country shippingCountry;

    @QueryInit("*")
    @ManyToOne
    @JoinColumn(name = "state_code", referencedColumnName = "code")
    private OrderState state;


}
