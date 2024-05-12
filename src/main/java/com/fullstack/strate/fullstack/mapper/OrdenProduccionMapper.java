package com.fullstack.strate.fullstack.mapper;

import com.fullstack.strate.fullstack.dto.OrdenProduccionDTO;
import com.fullstack.strate.fullstack.entity.OrdenProduccion;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = MateriaPrimaMapper.class)
public interface OrdenProduccionMapper {
    public OrdenProduccionMapper INSTANCE = Mappers.getMapper(OrdenProduccionMapper.class);
    //MateriaPrimaMapper materiaPrimaMapper = MateriaPrimaMapper.INSTANCE; // Inicialización del objeto materiaPrimaMapper

    @Mappings({
            @Mapping(source = "idOrdenProduccion", target = "idOrdenProduccion"),
            @Mapping(source = "ordenTipoProductoSolicitado.tipo", target = "tipoProductoSolicitado"),
            @Mapping(source = "ordenEstado.estado", target = "estado"),
            @Mapping(source = "ordenTipoLineaProduccion.linea", target = "tipoLineaProduccion"),
            @Mapping(source = "ordenDetallesMateriaprima", target = "materiaPrima"),
    })
    OrdenProduccionDTO getOrdenProduccionDTO(OrdenProduccion op);

    @InheritInverseConfiguration
    OrdenProduccion getOrdenProducciontEntity(OrdenProduccionDTO opDTO);

    List<OrdenProduccion> toGetOrdenProduccionEntityList(List<OrdenProduccionDTO> list);
    List<OrdenProduccionDTO> toGetOrdenProductionDTOList(List<OrdenProduccion> list);
}