package com.fullstack.strate.fullstack.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MateriaPrimaDTO {
    //private Integer idOrdenProduccion;
    private Integer idProducto;
    private String  materiaPrima;
    private String  descripcion;
    private Integer cantidadUtilizar;
    private String  unidad;

    @Override
    public String toString() {
        return "MateriaPrimaDTO{" +
                "idProducto=" + idProducto +
                ", materiaPrima='" + materiaPrima + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", cantidadUtilizar=" + cantidadUtilizar +
                ", unidad='" + unidad + '\'' +
                '}';
    }
}
