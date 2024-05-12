package com.fullstack.strate.fullstack.repository;


import com.fullstack.strate.fullstack.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<ProductoEntity, Integer> {

    @Modifying
    @Query(value = "{CALL spagmod_producto(:pNombre,:pDescripcion, :pIdTipoProducto, :pUnidad, :pExistencia,:pIdEstado, :pIdProducto, :pOpcion)}", nativeQuery = true)
    int agregrarModifcarProducto(
            @Param("pNombre")     String  pNombre,     @Param("pDescripcion") String  pDescripcion,  @Param("pIdTipoProducto") Integer pIdTipoProducto,
            @Param("pUnidad")     String  pUnidad,     @Param("pExistencia")  Integer pExistencia,  @Param("pIdEstado")       Integer pIdEstado,
            @Param("pIdProducto") Integer pIdProducto, @Param("pOpcion")      Integer pOpcion
            );

    @Modifying
    @Query(value = "{CALL speliminar_producto(:idproducto)}", nativeQuery = true)
    int eliminarProducto(@Param("idproducto") Integer idproducto);

    @Query(value = "SELECT istrategies.fnobtener_estado_orden_producto(:idproducto)", nativeQuery = true)
    String obtenerEstadoOrdenProducto(@Param("idproducto") Integer idProducto);

    @Query(value = "SELECT p from ProductoEntity p where p.productotipo.idTipoProducto= :tipoproducto", nativeQuery = false)
    List<ProductoEntity> getProductosPorTipo(@Param("tipoproducto") Integer tipoproducto);
}
