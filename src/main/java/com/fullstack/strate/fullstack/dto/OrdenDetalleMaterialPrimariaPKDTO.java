package com.fullstack.strate.fullstack.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrdenDetalleMaterialPrimariaPKDTO implements Serializable {
        private Integer idOrdenProduccion;
        private Integer idProducto;
    }

