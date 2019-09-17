package com.netisov.tim.commandes.domain;

import com.querydsl.core.annotations.QueryInit;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "article")
@Getter
@Cacheable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {
    @Id
    @NotNull
    @Column(name = "code")
    private String code;

    @NotNull
    @Size(max = 256)
    @Column(name = "label", unique = true)
    private String label;

    @NotNull
    @Positive
    @Column(name = "price")
    private Double price;

    @QueryInit("*")
    @NotNull
    @ManyToOne
    @JoinColumn(name = "family_code", referencedColumnName = "code")
    private ArticleFamily family;

@Builder
    public Article(String code, String label, Double price, ArticleFamily family) {
    Objects.requireNonNull(code);
    Objects.requireNonNull(label);
    Objects.requireNonNull(price);
    Objects.requireNonNull(family);
    this.code = code;
    this.label = label;
    this.price = price;
    this.family = family;
}

}
