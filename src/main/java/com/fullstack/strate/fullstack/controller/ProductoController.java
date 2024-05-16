package com.fullstack.strate.fullstack.controller;

import com.fullstack.strate.fullstack.dto.ApiResponse;
import com.fullstack.strate.fullstack.dto.ProductoDTO;
import com.fullstack.strate.fullstack.dto.ProductoUpdateDTO;
import com.fullstack.strate.fullstack.entity.ProductoEntity;
import com.fullstack.strate.fullstack.service.ProductoService;
import com.fullstack.strate.fullstack.utils.ApiResponseFinal;
import com.fullstack.strate.fullstack.utils.AuthorizationValidator;
import jakarta.validation.Valid;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin(origins = "**")
@RequestMapping(value = "/productos")
public class ProductoController {


    @Lazy
    @Autowired
    ProductoService productoService;

    @Autowired
    AuthorizationValidator authorizationValidator;

    @Autowired
    ApiResponseFinal apiResponseFinal;

    @GetMapping("/export-pdf/{tipoproducto}")
    public ResponseEntity<byte[]> exportPdf(@PathVariable("tipoproducto") Integer tipoproducto,@RequestHeader("Authorization") String jwt) throws JRException, FileNotFoundException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "productreport.pdf");
        return ResponseEntity.ok().headers(headers).body(this.productoService.exportPdf(tipoproducto));
    }

    @GetMapping("/list-type/{tipo}")
    public ResponseEntity<?> findByType(@PathVariable("tipo") Integer tipo, @RequestHeader("Authorization") String jwt){
        if (authorizationValidator.isValidAuth(jwt) != null) {
            System.out.println("validando token: "+ jwt);
            return ResponseEntity.status(HttpStatus.OK).body(this.productoService.getListProductsByTypoDTos(tipo));
        }
        return apiResponseFinal.buildApiResponse("token invalido o expirado", true, HttpStatus.UNAUTHORIZED, true, null);
    }

    @GetMapping("/find")
    public ResponseEntity<?> findByType(@RequestParam("idproducto") Integer idproducto,@RequestParam("cantusar") Integer cantusar, @RequestHeader("Authorization") String jwt){
        if (authorizationValidator.isValidAuth(jwt) != null) {
            ProductoEntity productoFound = this.productoService.findProductoById(idproducto);
            if (productoFound == null) {
                return apiResponseFinal.buildApiResponse("No existe el produccto", false, HttpStatus.NOT_FOUND, true, null);
            }
            if (productoFound.getExistencia() >= cantusar) {
                return apiResponseFinal.buildApiResponse("cantidad disponible: " + productoFound.getExistencia(), true, HttpStatus.OK, false, null);
            }
            return apiResponseFinal.buildApiResponse("La existencia del producto es menor a la requerida: " + productoFound.getExistencia(), false, HttpStatus.CONFLICT, true, null);
        }
        return apiResponseFinal.buildApiResponse("Token invalido o expirado", true, HttpStatus.UNAUTHORIZED, true, null);
    }

    @GetMapping("")
    public ResponseEntity<?> findAllDTOs(@RequestHeader("Authorization") String jwt) {
        if (authorizationValidator.isValidAuth(jwt) != null) {
        return ResponseEntity.status(HttpStatus.OK).body(this.productoService.getListProductsDTOs());
        }
        return apiResponseFinal.buildApiResponse("Token invalido o expirado", true, HttpStatus.UNAUTHORIZED, true, null);
    }

    @PostMapping("")
    public ResponseEntity<?> save(@Valid @RequestBody ProductoDTO nProd, BindingResult bindingResult, @RequestHeader("Authorization") String jwt) {
        if (authorizationValidator.isValidAuth(jwt) != null) {
            if (bindingResult.hasErrors()) {
                return ResponseEntity.badRequest().body(buildValidationErrorResponse(bindingResult));
            }
            int response = this.productoService.save(nProd);
        if (response < 1) {
            return apiResponseFinal.buildApiResponse("No se pudo ingresar", false, HttpStatus.OK, true, null);
        }
            return apiResponseFinal.buildApiResponse("ingresado", true, HttpStatus.OK, false, null);
        }
        return apiResponseFinal.buildApiResponse("Token invalido o expirado", true, HttpStatus.UNAUTHORIZED, true, null);
    }

    @PutMapping("/{idproducto}")
    public ResponseEntity<?> update(@Valid @PathVariable("idproducto") Integer idproducto, @Valid @RequestBody ProductoUpdateDTO uProd, BindingResult bindingResult,@RequestHeader("Authorization") String jwt) {
        if (authorizationValidator.isValidAuth(jwt) != null) {

        if (!this.productoService.existsByIdProducto(idproducto)) {
            return apiResponseFinal.buildApiResponse("No existe el produccto", false, HttpStatus.NOT_FOUND, true, null);
        }
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(buildValidationErrorResponse(bindingResult));
        }
        int response = this.productoService.update(idproducto,uProd);
        System.out.println("response: "+response);
        if (response <1) {
            return apiResponseFinal.buildApiResponse("No se pudo actualizar", false, HttpStatus.OK, true, null);
        }
        return apiResponseFinal.buildApiResponse("Actualizado exitosamente", true, HttpStatus.OK, false, null);
        }
        return apiResponseFinal.buildApiResponse("Token invalido o expirado", true, HttpStatus.UNAUTHORIZED, true, null);
    }

    @GetMapping("/orden-estado-producto/{idproducto}")
    public ResponseEntity<?> getmmesage(@PathVariable("idproducto") Integer idproducto, @RequestHeader("Authorization") String jwt) {
        if (authorizationValidator.isValidAuth(jwt) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(this.productoService.estadoProductoEnOrden(idproducto));
        }
        return apiResponseFinal.buildApiResponse("Token invalido o expirado", true, HttpStatus.UNAUTHORIZED, true, null);
    }

    @DeleteMapping("/{idproducto}")
    public ResponseEntity<?> delete(@PathVariable("idproducto") Integer idproducto, @RequestHeader("Authorization") String jwt) {
        if (authorizationValidator.isValidAuth(jwt) != null) {

        int result=0;
        String estadoOrden="";
        boolean exists=this.productoService.existsByIdProducto(idproducto);
        if (!exists) {
            return apiResponseFinal.buildApiResponse("No existe el produccto", false, HttpStatus.NOT_FOUND, true, null);
        }
        estadoOrden=this.productoService.estadoProductoEnOrden(idproducto);
        if (Objects.equals(estadoOrden, "En ninguna orden")){
            result =this.productoService.delete(idproducto);
            System.out.println("resultado: "+result);
            if (result == 0 || result == -1) {
                return apiResponseFinal.buildApiResponse("Eliminado exitosamente", false, HttpStatus.OK, true, null);
            }else{
                return apiResponseFinal.buildApiResponse("No se pudo eliminar el producto esta en orden de producción con estado:  "+estadoOrden
                        , false, HttpStatus.CONFLICT, true, null);
            }
        }
        return apiResponseFinal.buildApiResponse("No se pudo eliminar el producto esta en orden de producción con estado:  "+estadoOrden, false, HttpStatus.CONFLICT, true, null);
        }
        return apiResponseFinal.buildApiResponse("Token invalido o expirado", true, HttpStatus.UNAUTHORIZED, true, null);
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


