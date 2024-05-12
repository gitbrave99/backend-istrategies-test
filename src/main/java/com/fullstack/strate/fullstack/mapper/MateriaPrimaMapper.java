package com.fullstack.strate.fullstack.mapper;

import com.fullstack.strate.fullstack.dto.MateriaPrimaDTO;
import com.fullstack.strate.fullstack.entity.OrdenDetalleMateriaprima;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {OrdenProduccionMapper.class})
public interface MateriaPrimaMapper {
    public MateriaPrimaMapper INSTANCE = Mappers.getMapper(MateriaPrimaMapper.class);

    @Mappings({
            @Mapping(source = "producto.idProducto", target = "idProducto"),
            @Mapping(source = "producto.nombre", target = "materiaPrima"),
            @Mapping(source = "producto.descripcion", target = "descripcion"),
            @Mapping(source = "cantidadUtilizar", target = "cantidadUtilizar"),
            @Mapping(source = "producto.unidad", target = "unidad")
    })
    MateriaPrimaDTO toMateriaPrimaDTO(OrdenDetalleMateriaprima dtMatprima);

    @InheritInverseConfiguration
    OrdenDetalleMateriaprima toEntity(MateriaPrimaDTO matp);

    List<MateriaPrimaDTO> toGetProductList(List<OrdenDetalleMateriaprima> productList);

    List<OrdenDetalleMateriaprima> toEntityList(List<MateriaPrimaDTO> getProductList);


}
