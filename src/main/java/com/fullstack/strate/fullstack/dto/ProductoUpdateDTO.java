package com.fullstack.strate.fullstack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductoUpdateDTO {
    @NotNull
    @NotBlank
    private String descripcion;
    @Positive(message = "La existencia debe ser mayor a cero")
    private int existencia;
    @NotNull
    private Integer idestado;
}
