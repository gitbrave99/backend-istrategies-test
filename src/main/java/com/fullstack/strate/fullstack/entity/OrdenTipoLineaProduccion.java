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
@Table(name = "orden_tipo_linea_produccion")
public class OrdenTipoLineaProduccion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden_tipo_linea_produccion")
    private Integer idOrdenTipoLinePproduccion;

    private String linea;
}