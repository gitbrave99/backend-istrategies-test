package com.fullstack.strate.fullstack.entity;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Data
public class OrdenDetalleMaterialPrimariaPK implements Serializable {
    @Column(name = "id_orden_produccion")
    private Integer idOrdenProduccion;
    @Column(name = "id_producto")
    private Integer idProducto;
}
