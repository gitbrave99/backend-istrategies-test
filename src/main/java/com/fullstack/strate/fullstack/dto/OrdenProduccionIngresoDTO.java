package com.fullstack.strate.fullstack.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrdenProduccionIngresoDTO {
    @NotNull(message = "Cliente no válido")
    @NotBlank(message = "Cliente no válido")
    private String  cliente;

    @NotNull(message = "Fecha Válida")
    //@DateTimeFormat(pattern = "yyyy-MM-dd") // Formato de la fecha77
    private String    fechaEntrega;

    @NotNull(message = "Tipo producto no válido")
    @Positive(message = "Tipo producto no válido")
    private Integer idTipoProductoSolicitado;

    @NotNull(message = "Cantidad no válida")
    @Positive(message = "Cantidad no válida")
    private Integer cantidadSolicitada;

    private List<MateriaPrimaDTO> materiaPrima;
}
