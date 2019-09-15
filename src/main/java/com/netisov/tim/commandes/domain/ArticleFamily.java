package com.netisov.tim.commandes.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "article_family")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleFamily {
    @Id
    @Column(name="code")
    private String code;

    @NotNull
    @Size(max = 256)
    @Column(name = "label", unique = true)
    private String label;

}
