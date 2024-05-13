package com.fullstack.strate.fullstack.repository;

import com.fullstack.strate.fullstack.entity.OrdenProduccion;
import com.fullstack.strate.fullstack.entity.ProductoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface OrdenProduccionRepository extends JpaRepository<OrdenProduccion, Integer> {
    @Modifying
    @Query(value = "{CALL gestion_orden_produccion(:pCliente, :pfechaEntrega, :pTipoProSolic, :pCantSoli, :pFechaEnProduccion, :pLineaProduccion, :pFechaFinalizacion, :pIdOrdenProduccion, :pOpcion)}", nativeQuery = true)
    int gestionOrdenProduccion(
      @Param("pCliente")           String  pCliente,           @Param("pfechaEntrega")      String pfechaEntrega,         @Param("pTipoProSolic")    Integer pTipoProSolic,
      @Param("pCantSoli")          Integer pCantSoli,          @Param("pFechaEnProduccion") String pFechaEnProduccion,    @Param("pLineaProduccion") Integer pLineaProduccion,
      @Param("pFechaFinalizacion") String  pFechaFinalizacion, @Param("pIdOrdenProduccion") Integer pIdOrdenProduccion, @Param("pOpcion")          Integer pOpcion
    );

    @Modifying
    @Query(value = "{CALL gestion_det_materiaprima(:pidordenproduccion, :idproducto, :pcantutilizar)}", nativeQuery = true)
    int gestionDetalleMatPrima(@Param("pidordenproduccion") Integer pidordenproduccion,@Param("idproducto") Integer idproducto, @Param("pcantutilizar") Integer  pcantutilizar);

    @Query(value = "SELECT p from OrdenProduccion p where p.fechaEntrega = :fechaentrega and p.ordenEstado.idOrdenEstado = :estado", nativeQuery = false)
    List<OrdenProduccion> getOrdenFechaEntregaEstado(@Param("fechaentrega") Date fechaentrega,@Param("estado") Integer estado);

    @Query(value = "SELECT p from OrdenProduccion p where p.fechaEntrega = :fechaentrega", nativeQuery = false)
    List<OrdenProduccion> getOrdenFechaEntrega(@Param("fechaentrega") Date fechaentrega);

    @Query(value = "select id_orden_produccion  from orden_produccion order by id_orden_produccion desc limit 1",  nativeQuery = true)
    Integer getLastIdOrdenProduccion();

}
