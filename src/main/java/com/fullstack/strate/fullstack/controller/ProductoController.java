package com.fullstack.strate.fullstack.controller;

import com.fullstack.strate.fullstack.dto.ApiResponse;
import com.fullstack.strate.fullstack.dto.ProductoDTO;
import com.fullstack.strate.fullstack.dto.ProductoUpdateDTO;
import com.fullstack.strate.fullstack.entity.ProductoEntity;
import com.fullstack.strate.fullstack.repository.ProductoRepository;
import com.fullstack.strate.fullstack.service.ProductoService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.IllegalFormatCodePointException;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping(value = "/productos")
public class ProductoController {

    /*  @Lazy Este mecanismo, en oposición con el prematuro, inicializa el bean (crea una instancia de
        la clase “ProductoServiceImpl”) sólo bajo demanda. Es decir, solo cuando se ejecute el endpoint
        REST que hace uso del servicio. Para probar este mecanismo anotamos la referencia en el
        controller con la anotación @Lazy. tambien es neceario agregarlo al implement
    */
    @Lazy
    @Autowired
    ProductoService productoService;

    @GetMapping("/export-pdf/{tipoproducto}")
    public ResponseEntity<byte[]> exportPdf(@PathVariable("tipoproducto") Integer tipoproducto) throws JRException, FileNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "productreport.pdf");
        return ResponseEntity.ok().headers(headers).body(this.productoService.exportPdf(tipoproducto));
    }

    @GetMapping("/list-type/{tipo}")
    public ResponseEntity<?> findByType(@PathVariable("tipo") Integer tipo){
        return ResponseEntity.status(HttpStatus.OK).body(this.productoService.getListProductsByTypoDTos(tipo));
    }

    @GetMapping("/find")
    public ResponseEntity<?> findByType(@RequestParam("idproducto") Integer idproducto,@RequestParam("cantusar") Integer cantusar){
        ProductoEntity productoFound= this.productoService.findProductoById(idproducto);
        if (productoFound == null) {
            return buildApiResponse2("No existe el produccto", false, HttpStatus.NOT_FOUND, true, null);
        }
        if (productoFound.getExistencia()>=cantusar) {
            return buildApiResponse2("cantidad disponible: "+ productoFound.getExistencia(), true, HttpStatus.OK, false, null);
        }
        return buildApiResponse2("La existencia del producto es menor a la requerida: "+ productoFound.getExistencia(), false, HttpStatus.CONFLICT, true, null);
    }

    @GetMapping("")
    public ResponseEntity<?> findAllDTOs() {
        return ResponseEntity.status(HttpStatus.OK).body(this.productoService.getListProductsDTOs());
    }

    @PostMapping("")
    public ResponseEntity<?> save(@Valid @RequestBody ProductoDTO nProd, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(buildValidationErrorResponse(bindingResult));
        }
        int response = this.productoService.save(nProd);
        if (response < 1) {
            return buildApiResponse("No se pudo ingresar", false, HttpStatus.OK.value(), true, null);
        }

        return buildApiResponse("ingresado", true, HttpStatus.OK.value(), false, null);
    }

    @PutMapping("/{idproducto}")
    public ResponseEntity<?> update(@Valid @PathVariable("idproducto") Integer idproducto, @Valid @RequestBody ProductoUpdateDTO uProd, BindingResult bindingResult) {
        System.out.println("id= "+ idproducto);
        if (!this.productoService.existsByIdProducto(idproducto)) {
            return buildApiResponse("No existe el produccto", false, HttpStatus.NOT_FOUND.value(), true, null);
        }
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(buildValidationErrorResponse(bindingResult));
        }
        int response = this.productoService.update(idproducto,uProd);
        System.out.println("response: "+response);
        if (response <1) {
            return buildApiResponse("No se pudo actualizar", false, HttpStatus.OK.value(), true, null);
        }
        return buildApiResponse("Actualizado exitosamente", true, HttpStatus.OK.value(), false, null);
    }

    @GetMapping("/orden-estado-producto/{idproducto}")
    public ResponseEntity<?> getmmesage(@PathVariable("idproducto") Integer idproducto) {
        return ResponseEntity.status(HttpStatus.OK).body(this.productoService.estadoProductoEnOrden(idproducto));
    }

    @DeleteMapping("/{idproducto}")
    public ResponseEntity<?> delete(@PathVariable("idproducto") Integer idproducto) {
        int result=0;
        String estadoOrden="";
        boolean exists=this.productoService.existsByIdProducto(idproducto);
        if (!exists) {
            return buildApiResponse("No existe el produccto", false, HttpStatus.NOT_FOUND.value(), true, null);
        }
        estadoOrden=this.productoService.estadoProductoEnOrden(idproducto);
        if (Objects.equals(estadoOrden, "En ninguna orden")){
            result =this.productoService.delete(idproducto);
            System.out.println("resultado: "+result);
            if (result == 0 || result == -1) {
                return buildApiResponse("Eliminado exitosamente", false, HttpStatus.OK.value(), true, null);
            }else{
                return buildApiResponse("No se pudo eliminar el producto esta en orden de producción con estado:  "+estadoOrden
                        , false, HttpStatus.CONFLICT.value(), true, null);
            }
        }
        return buildApiResponse("No se pudo eliminar el producto esta en orden de producción con estado:  "+estadoOrden, false, HttpStatus.CONFLICT.value(), true, null);
    }

    public static ResponseEntity<Map<String, String>> handleValidationErrors(BindingResult bindingResult) {
        Map<String, String> errores = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errores.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errores);
    }
    private ResponseEntity<Object> buildApiResponse(String message, boolean success, int code, boolean error, Object data) {
        ApiResponse response = new ApiResponse(message, success, code, error, data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private ResponseEntity<Object> buildApiResponse2(String message, boolean success, HttpStatus code, boolean error, Object data) {
        ApiResponse response = new ApiResponse(message, success, code.value(), error, data);
        return ResponseEntity.status(code).body(response);
    }


    private Map<String, Object> buildValidationErrorResponse(BindingResult bindingResult) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Errores de validación");
        response.put("success", false);
        response.put("code", HttpStatus.BAD_REQUEST.value());
        response.put("error", true);
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        response.put("data", errors);
        return response;
    }

}


