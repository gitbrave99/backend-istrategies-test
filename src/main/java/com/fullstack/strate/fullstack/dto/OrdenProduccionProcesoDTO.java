package com.fullstack.strate.fullstack.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrdenProduccionProcesoDTO {
    @NotNull(message = "Fecha Válida")
    @DateTimeFormat(pattern = "yyyy-MM-dd") // Formato de la fecha77
    private Date    fechaEnProduccion;

    @NotNull(message = "Tipo linea no válido")
    @Positive(message = "Tipo linea no válido")
    private Integer idTipoLinea;
}
