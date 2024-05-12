package com.fullstack.strate.fullstack.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orden_produccion")
public class OrdenProduccion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden_produccion")
    private Integer idOrdenProduccion;

    private String cliente;

    @Column(name = "fecha_entrega")
    private Date fechaEntrega;

    @ManyToOne
    @JoinColumn(name = "id_orden_tipo_producto_solicitado")
    private OrdenTipoProductoSolicitado ordenTipoProductoSolicitado;

    /*@Column(name = "id_orden_tipo_producto_solicitado")
    private  Integer idOrdenTipoProductoSolicitado;*/

    @Column(name = "cantidad_solicitada")
    private Integer cantidadSolicitada;

    @ManyToOne
    @JoinColumn(name = "id_orden_estado")
    private OrdenEstado ordenEstado;
    /*
    @Column(name = "id_orden_estado")
    private Integer idOrdenEstado;*/

    @Column(name = "fecha_en_produccion")
    private Date fechaEnProduccion;

    @ManyToOne
    @JoinColumn(name = "id_orden_tipo_linea_produccion")
    private OrdenTipoLineaProduccion ordenTipoLineaProduccion;
    /*@Column(name = "id_orden_tipo_linea_produccion")
    private Integer idOrdenTipoLineaProduccion;*/

    @Column(name = "fecha_finalizacion")
    private Date fechaFinalizacion;

    @OneToMany(mappedBy = "orden_produccion", fetch = FetchType.LAZY)
    private List<OrdenDetalleMateriaprima> ordenDetallesMateriaprima;
}
