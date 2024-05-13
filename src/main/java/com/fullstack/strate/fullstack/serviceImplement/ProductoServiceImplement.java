package com.fullstack.strate.fullstack.serviceImplement;

import com.fullstack.strate.fullstack.dto.ProductoDTO;
import com.fullstack.strate.fullstack.dto.ProductoUpdateDTO;
import com.fullstack.strate.fullstack.entity.ProductoEntity;
import com.fullstack.strate.fullstack.mapper.ProductMapper;
import com.fullstack.strate.fullstack.repository.ProductoRepository;
import com.fullstack.strate.fullstack.service.ProductoService;
import com.fullstack.strate.fullstack.utils.ProductReporterGenerator;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Lazy
@Service
public class ProductoServiceImplement implements ProductoService {

    @Autowired
    ProductoRepository productoRepository;
    private ProductMapper productMapper = ProductMapper.INSTANCE;

    @Autowired
    ProductReporterGenerator reporterGenerator;

    @Override
    public ProductoEntity findProductoById(Integer idProducto) {
        return this.productoRepository.findByIdProducto(idProducto);
    }

    @Override
    public ArrayList<ProductoDTO> getListProductsDTOs() {
     return new ArrayList<>(this.productMapper.toGetProductDTOList(this.productoRepository.findAll()));
    }

    @Override
    public ArrayList<ProductoDTO> getListProductsByTypoDTos(Integer tipo) {
        return new ArrayList<>(this.productMapper.toGetProductDTOList(this.productoRepository.getProductosPorTipo(tipo)));
    }

    @Transactional()
    @Override
    public int save(ProductoDTO prod) {
        return this.productoRepository.agregrarModifcarProducto(prod.getNombre(), prod.getDescripcion(),Integer.valueOf(prod.getTipo()),prod.getUnidad(),prod.getExistencia(),Integer.valueOf(prod.getEstado()),0,0);
    }

    @Transactional()
    @Override
    public int update(Integer idproducto, ProductoUpdateDTO prod) {
        return this.productoRepository.agregrarModifcarProducto("", prod.getDescripcion(),0,"",prod.getExistencia(),prod.getIdestado(),idproducto,1);
    }

    @Transactional()
    @Override
    public int delete(Integer idProducto) {
         return this.productoRepository.eliminarProducto(idProducto);
    }

    @Override
    public String estadoProductoEnOrden(Integer idproducto) {
        return this.productoRepository.obtenerEstadoOrdenProducto(idproducto);
    }

    @Override
    public boolean existsByIdProducto(Integer idProducto) {
        return this.productoRepository.existsById(idProducto);
    }

    @Override
    public byte[] exportPdf(Integer tipoproducto) throws JRException, FileNotFoundException {
        if (tipoproducto==0){
            return reporterGenerator.exportToPdf(this.productMapper.toGetProductDTOList(this.productoRepository.findAll()),"productos");
        }
        return reporterGenerator.exportToPdf(this.productMapper.toGetProductDTOList(this.productoRepository.getProductosPorTipo(tipoproducto)),"productos");
    }

}
