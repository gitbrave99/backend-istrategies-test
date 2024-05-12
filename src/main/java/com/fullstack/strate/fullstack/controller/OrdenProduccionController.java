package com.fullstack.strate.fullstack.controller;

import com.fullstack.strate.fullstack.dto.ApiResponse;
import com.fullstack.strate.fullstack.dto.OrdenProduccionFinalizadaDTO;
import com.fullstack.strate.fullstack.dto.OrdenProduccionIngresoDTO;
import com.fullstack.strate.fullstack.dto.OrdenProduccionProcesoDTO;
import com.fullstack.strate.fullstack.service.OrdenProduccionService;
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
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping(value = "/orden-produccion")
public class OrdenProduccionController {

    @Lazy
    @Autowired
    OrdenProduccionService ops;


    @GetMapping("/estado-fecha")
    public ResponseEntity<?> findAll(@RequestParam("estado") Integer estado, @RequestParam("fecha") Date fecha){
        return ResponseEntity.status(HttpStatus.OK).body(this.ops.findOrdenFechaEntregaEstado(fecha, estado));
    }

    @GetMapping("/date")
    public ResponseEntity<?> findAllByDate(@RequestParam("date") Date date){
        return ResponseEntity.status(HttpStatus.OK).body(this.ops.findAllByDate(date));
    }

    @GetMapping("")
    public ResponseEntity<?> findAllDTOs(){
        return ResponseEntity.status(HttpStatus.OK).body(this.ops.findAllDTO());
    }

    @GetMapping("/export-pdf-all")
    public ResponseEntity<byte[]> exportPdfAll() throws JRException, FileNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "orderreport.pdf");
        return ResponseEntity.ok().headers(headers).body(this.ops.exportPdfAll());
    }

    @GetMapping("/export-pdf")
    public ResponseEntity<byte[]> exportPdf(@RequestParam("status") Integer status, @RequestParam("date") Date date) throws JRException, FileNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "orderreport.pdf");
        return ResponseEntity.ok().headers(headers).body(this.ops.exportPdf(status, date));
    }

    @GetMapping("/export-pdf-date")
    public ResponseEntity<byte[]> exportPdfByDate(@RequestParam("date") Date date) throws JRException, FileNotFoundException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "orderreport.pdf");
        return ResponseEntity.ok().headers(headers).body(this.ops.exportPdfByDate(date));
    }

    @PostMapping("")
    public ResponseEntity<?> create(@Valid @RequestBody OrdenProduccionIngresoDTO op, BindingResult bindingResult){
        // VALIDAR EXISTENCIA DE MATERIAL PRIMA ANTES DE GENERAR ORDEN
        if (bindingResult.hasErrors()) {
            System.out.println("binding rsults: ");
            return ResponseEntity.badRequest().body(buildValidationErrorResponse(bindingResult));
        }
        System.out.println("recpciones: "+ op.toString());
        if (this.ops.crearOrden(op) <1) {
            return buildApiResponse("No se pudo ingresar", false, HttpStatus.OK.value(), true, null);
        }
        return buildApiResponse("Igresado exitosamente", true, HttpStatus.OK.value(), false, null);
    }

    @PostMapping("/proceso/{idOrdenProduccion}")
    public ResponseEntity<?> proceso_orden(@PathVariable("idOrdenProduccion") Integer idOrdenProduccion,@Valid @RequestBody OrdenProduccionProcesoDTO op){
        System.out.println("recpciones: "+ op.toString());
        if (!this.ops.existsOrdenProducccionById(idOrdenProduccion)) {
            return buildApiResponse("No existe orden", false, HttpStatus.OK.value(), false, null);
        }
        if (this.ops.crearOrdenEnProduccion(idOrdenProduccion,op) <1) {
            return buildApiResponse("No se puedo actualizar", false, HttpStatus.OK.value(), false, null);
        }
        return buildApiResponse("Actualizado correctamente", true, HttpStatus.OK.value(), false, null);
    }

    @PostMapping("/finalizada/{idOrdenProduccion}")
    public ResponseEntity<?> finalizada_orden(@PathVariable("idOrdenProduccion") Integer idOrdenProduccion, @RequestBody OrdenProduccionFinalizadaDTO op){
        System.out.println("recpciones: "+ op.toString());
        if (!this.ops.existsOrdenProducccionById(idOrdenProduccion)) {
            return buildApiResponse("No existe orden", false, HttpStatus.OK.value(), false, null);
        }
        if (this.ops.crearOrdenEnFinalizar(idOrdenProduccion,op) <1) {
            return buildApiResponse("No se puedo actualizar", false, HttpStatus.OK.value(), true, null);
        }
        return buildApiResponse("Actualizado correctamente", true, HttpStatus.OK.value(), false, null);
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

    private ResponseEntity<Object> buildApiResponse(String message, boolean success, int code, boolean error, Object data) {
        ApiResponse response = new ApiResponse(message, success, code, error, data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
