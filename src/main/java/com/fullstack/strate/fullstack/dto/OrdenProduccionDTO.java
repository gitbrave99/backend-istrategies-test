package com.fullstack.strate.fullstack.dto;

import com.fullstack.strate.fullstack.entity.OrdenDetalleMateriaprima;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdenProduccionDTO {

    private Integer idOrdenProduccion;
    private String  cliente;
    private Date    fechaEntrega;
    private String  tipoProductoSolicitado;
    private Integer cantidadSolicitada;
    private String  estado;
    private Date    fechaEnProduccion;
    private String  tipoLineaProduccion;
    private Date    fechaFinalizacion;
    private List<MateriaPrimaDTO> materiaPrima;

}
