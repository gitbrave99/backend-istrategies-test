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
@Table(name = "producto_tipo")
public class ProductoTipoEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto_tipo")
    private Integer idTipoProducto;

    private String tipo;

   /* @OneToMany(mappedBy = "tipoproducto")
    @JsonIgnore(value = true)
    private Collection<ProductoEntity> productsByIdCategory;*/
}
