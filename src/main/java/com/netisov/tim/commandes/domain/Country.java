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
@Table(name = "country")
@Getter
@Cacheable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Country {

    @Id
    @NotNull
    @Column(name="code")
    private String code;

    @NotNull
    @Size(max = 256)
    @Column(name = "label", unique = true)
    private String label;

    @Builder
    public Country(String code, String label) {
        Objects.requireNonNull(code);
        Objects.requireNonNull(label);
        this.code = code;
        this.label = label;
    }
}
