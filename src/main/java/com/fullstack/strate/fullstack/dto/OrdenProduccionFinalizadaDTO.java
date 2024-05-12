package com.fullstack.strate.fullstack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrdenProduccionFinalizadaDTO {
    @NotEmpty(message = "La fecha de finalización no puede estar vacía")
    @NotNull(message = "La fecha de finalización no puede ser nula")
    private Date fechaFinalizacion;
}
