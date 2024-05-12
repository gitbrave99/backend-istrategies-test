package com.fullstack.strate.fullstack.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tokens")
public class Token implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_token")
    private Integer id_token;
    private String token;
    private boolean expired;
    private boolean anulado;

    @Override
    public String toString() {
        return "Token{" +
                "id_token=" + id_token +
                ", token='" + token + '\'' +
                ", expired=" + expired +
                ", anulado=" + anulado +
                '}';
    }
}
