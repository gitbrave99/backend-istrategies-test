package com.fullstack.strate.fullstack.mapper;

import com.fullstack.strate.fullstack.dto.ProductoDTO;
import com.fullstack.strate.fullstack.entity.ProductoEntity;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {

    public ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    @Mappings({
            @Mapping(source = "idProducto",     target = "idProducto"),
            @Mapping(source = "productotipo.tipo",   target = "tipo"),
            @Mapping(source = "productoestado.estado", target = "estado"),
    })
    ProductoDTO getProductDTO(ProductoEntity producto);

    @InheritInverseConfiguration
    ProductoEntity getProductEntity(ProductoDTO productoDTO);

    List<ProductoEntity> toGetProductEntityList(List<ProductoDTO> list);
    List<ProductoDTO> toGetProductDTOList(List<ProductoEntity> list);

}
