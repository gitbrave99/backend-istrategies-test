package com.fullstack.strate.fullstack.service;

import com.fullstack.strate.fullstack.dto.ProductoDTO;
import com.fullstack.strate.fullstack.dto.ProductoUpdateDTO;
import com.fullstack.strate.fullstack.entity.ProductoEntity;
import net.sf.jasperreports.engine.JRException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface ProductoService {
    ProductoEntity findProductoById(Integer idProducto);
    ArrayList<ProductoDTO> getListProductsDTOs();
    ArrayList<ProductoDTO> getListProductsByTypoDTos(Integer tipo);
    int save(ProductoDTO prod);
    int update(Integer idproducto, ProductoUpdateDTO prod);
    int delete(Integer idProducto);
    String estadoProductoEnOrden(Integer idproducto);
    boolean existsByIdProducto(Integer idProducto);
    byte[] exportPdf(Integer tipoproducto) throws JRException, FileNotFoundException;
}
