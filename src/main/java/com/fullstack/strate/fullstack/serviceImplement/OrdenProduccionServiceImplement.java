package com.fullstack.strate.fullstack.serviceImplement;

import com.fullstack.strate.fullstack.dto.OrdenProduccionDTO;
import com.fullstack.strate.fullstack.dto.OrdenProduccionFinalizadaDTO;
import com.fullstack.strate.fullstack.dto.OrdenProduccionIngresoDTO;
import com.fullstack.strate.fullstack.dto.OrdenProduccionProcesoDTO;
import com.fullstack.strate.fullstack.entity.OrdenProduccion;
import com.fullstack.strate.fullstack.mapper.MateriaPrimaMapper;
import com.fullstack.strate.fullstack.mapper.OrdenProduccionMapper;
import com.fullstack.strate.fullstack.repository.OrdenProduccionRepository;
import com.fullstack.strate.fullstack.service.OrdenProduccionService;
import com.fullstack.strate.fullstack.utils.ProductReporterGenerator;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileNotFoundException;
import java.sql.Date;
import java.util.ArrayList;

@Lazy
@Service
public class OrdenProduccionServiceImplement implements OrdenProduccionService {

    @Autowired
    OrdenProduccionRepository opr;
    @Autowired
    private OrdenProduccionMapper opMapper;

    @Autowired
    ProductReporterGenerator reporterGenerator;

    @Override
    public ArrayList<OrdenProduccionDTO> findAllByDate(Date date) {
        return new ArrayList<>(opMapper.toGetOrdenProductionDTOList(this.opr.getOrdenFechaEntrega(date)));
    }

    @Override
    public ArrayList<OrdenProduccionDTO> findOrdenFechaEntregaEstado(Date fechaEntrega, Integer estado) {
        return new ArrayList<>(this.opMapper.toGetOrdenProductionDTOList(this.opr.getOrdenFechaEntregaEstado(fechaEntrega, estado)));
    }


    @Override
    public ArrayList<OrdenProduccionDTO> findAllDTO() {
        return new ArrayList<>(opMapper.toGetOrdenProductionDTOList(this.opr.findAll()));

    }

    @Transactional(readOnly = false)
    @Override
    public int crearOrden(OrdenProduccionIngresoDTO op) {

        return this.opr.gestionOrdenProduccion(op.getCliente(), op.getFechaEntrega(), op.getIdTipoProductoSolicitado(), op.getCantidadSolicitada(),
                null, 0,null, 1,1);
    }

    @Override
    public int getLastIdOrdenProduccion() {
        return this.opr.getLastIdOrdenProduccion();
    }

    @Transactional(readOnly = false)
    @Override
    public int crearOrdenEnProduccion(Integer idOrdenProduccion, OrdenProduccionProcesoDTO op) {
        return this.opr.gestionOrdenProduccion("", null, 0, 0,
                op.getFechaEnProduccion(), op.getIdTipoLinea(),null, idOrdenProduccion,2);
    }

    @Transactional(readOnly = false)
    @Override
    public int crearDetalleOrdenProduccion(Integer idOrdenProduccion, Integer idProducto, Integer cantidadUsar) {
        return this.opr.gestionDetalleMatPrima(idOrdenProduccion, idProducto, cantidadUsar);
    }

    @Transactional(readOnly = false)
    @Override
    public int crearOrdenEnFinalizar(Integer idOrdenProduccion, OrdenProduccionFinalizadaDTO op) {
        return this.opr.gestionOrdenProduccion("", null, 0, 0,
                null, 0,op.getFechaFinalizacion(), idOrdenProduccion,3);
    }

    @Override
    public boolean existsOrdenProducccionById(Integer idOrdenProduccion) {
        return this.opr.existsById(idOrdenProduccion);
    }

    @Override
    public byte[] exportPdf(Integer estado,Date fecha) throws JRException, FileNotFoundException {
            return reporterGenerator.exportToPdf(opMapper.toGetOrdenProductionDTOList(this.opr.getOrdenFechaEntregaEstado(fecha, estado)),"ordenproduccion");
    }

    @Override
    public byte[] exportPdfByDate(Date fecha) throws JRException, FileNotFoundException {
        return reporterGenerator.exportToPdf(opMapper.toGetOrdenProductionDTOList(this.opr.getOrdenFechaEntrega(fecha)),"ordenproduccion");
    }

    @Override
    public byte[] exportPdfAll() throws JRException, FileNotFoundException {
        return reporterGenerator.exportToPdf(opMapper.toGetOrdenProductionDTOList(this.opr.findAll()),"ordenproduccion");
    }


}
