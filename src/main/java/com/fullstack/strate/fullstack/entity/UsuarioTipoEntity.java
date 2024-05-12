package com.fullstack.strate.fullstack.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "usuario_tipo")
public class UsuarioTipoEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario_tipo")
    private Integer idUsuarioTipo;

    private String tipo;

/*    @OneToOne(mappedBy = "tipoUsuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private UsuarioEntity usuario;*/

}
