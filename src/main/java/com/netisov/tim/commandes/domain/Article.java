package com.netisov.tim.commandes.domain;

import com.querydsl.core.annotations.QueryInit;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Entity
@Table(name = "article")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {
    @Id
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


}
