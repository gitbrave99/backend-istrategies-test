package com.fullstack.strate.fullstack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Builder // para utilizar el patron builder
public class ProductoDTO {
    private Integer idProducto;
    @NotNull
    @NotBlank
    private String nombre;
    @NotNull
    @NotBlank
    private String descripcion;
    @NotNull
    @NotBlank
    private String tipo; // id_tipo_producot
    @NotNull
    @NotBlank
    private String unidad;
    @Positive(message = "La existencia debe ser mayor a cero")
    private int existencia;
    @NotNull
    @NotBlank
    private String estado;
}
