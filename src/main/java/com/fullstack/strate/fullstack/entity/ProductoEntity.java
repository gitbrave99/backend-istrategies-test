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
@Table(name = "producto")
public class ProductoEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Integer idProducto;

    private String nombre;
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "id_producto_tipo", referencedColumnName = "id_producto_tipo")
    private ProductoTipoEntity productotipo;
    //private int id_tipo_producto;

    private String unidad;
    private int existencia;

    @ManyToOne
    @JoinColumn(name = "id_producto_estado", referencedColumnName = "id_producto_estado")
    private ProductoEstadoEntity productoestado;
    /*private int id_estado_producto;*/

}

