package com.netisov.tim.commandes.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "order_state")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Cacheable
public class OrderState {

    @Id
    @NotNull
    @Column(name="code")
    private String code;

    @NotNull
    @Size(max = 256)
    @Column(name = "label", unique = true)
    private String label;

    @Column(name = "outcome", nullable = false)
    private OrderOutcome outcome;

    @Builder
    public OrderState(String code, String label, OrderOutcome outcome) {
        Objects.requireNonNull(code);
        Objects.requireNonNull(label);
        Objects.requireNonNull(outcome);
        this.code = code;
        this.label = label;
        this.outcome = outcome;
    }

}
