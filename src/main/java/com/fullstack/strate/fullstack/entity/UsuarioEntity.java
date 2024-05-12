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
@Table(name = "usuario")
public class UsuarioEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    private String username;

    private String password;

    /*@Column(name = "id_usuario_tipo")
    private Integer idUsuarioTipo;*/
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_usuario_tipo", referencedColumnName = "id_usuario_tipo")
    private UsuarioTipoEntity tipoUsuario;


}
