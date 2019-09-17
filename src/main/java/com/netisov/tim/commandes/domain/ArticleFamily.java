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
@Table(name = "article_family")
@Getter
@Cacheable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleFamily {
    @Id
    @NotNull
    @Column(name="code")
    private String code;

    @NotNull
    @Size(max = 256)
    @Column(name = "label", unique = true)
    private String label;

    @Builder
    public ArticleFamily(String code, String label) {
        Objects.requireNonNull(code);
        Objects.requireNonNull(label);
        this.code = code;
        this.label = label;
    }

}
