package com.fullstack.strate.fullstack.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orden_detalle_materiaprima")
public class OrdenDetalleMateriaprima implements Serializable {
    @EmbeddedId
    private OrdenDetalleMaterialPrimariaPK id;

    @Column(name = "cantidad_utilizar")
    private Integer cantidadUtilizar;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "id_orden_produccion", referencedColumnName = "id_orden_produccion", insertable = false, updatable = false),
    })
    @JsonIgnore
    private OrdenProduccion orden_produccion;

    @ManyToOne
    @JoinColumn(name = "id_producto", referencedColumnName = "id_producto",insertable = false, updatable = false)
    private ProductoEntity producto;
}


