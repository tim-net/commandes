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
@Table(name = "client")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @NotNull
    @Size(max = 8)
    @Column(name = "code")
    private String code;

    @NotNull
    @Size(max = 256)
    @Column(name = "name")
    private String name;

    @Builder
    public Client(Long id, String code, String name) {
        Objects.requireNonNull(code);
        Objects.requireNonNull(name);
        this.code = code;
        this.name = name;
    }

}
