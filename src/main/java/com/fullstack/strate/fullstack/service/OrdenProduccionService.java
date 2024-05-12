package com.fullstack.strate.fullstack.service;

import com.fullstack.strate.fullstack.dto.OrdenProduccionDTO;
import com.fullstack.strate.fullstack.dto.OrdenProduccionFinalizadaDTO;
import com.fullstack.strate.fullstack.dto.OrdenProduccionIngresoDTO;
import com.fullstack.strate.fullstack.dto.OrdenProduccionProcesoDTO;
import com.fullstack.strate.fullstack.entity.OrdenProduccion;
import net.sf.jasperreports.engine.JRException;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileNotFoundException;
import java.sql.Date;
import java.util.ArrayList;

public interface OrdenProduccionService {
    ArrayList<OrdenProduccionDTO> findAllByDate(Date date);
    ArrayList<OrdenProduccionDTO> findOrdenFechaEntregaEstado(Date fechaEntrega, Integer estado);
    ArrayList<OrdenProduccionDTO> findAllDTO();
    int crearOrden(OrdenProduccionIngresoDTO op);

    int crearOrdenEnProduccion(Integer idOrdenProduccion,OrdenProduccionProcesoDTO op);
    int crearOrdenEnFinalizar(Integer idOrdenProduccion, OrdenProduccionFinalizadaDTO op);
    boolean existsOrdenProducccionById(Integer idOrdenProduccion);
    byte[] exportPdf(Integer estado,Date fecha) throws JRException, FileNotFoundException;
    byte[] exportPdfByDate(Date fecha) throws JRException, FileNotFoundException;
    byte[] exportPdfAll() throws JRException, FileNotFoundException;
}
