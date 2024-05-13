package com.fullstack.strate.fullstack.utils;

import com.fullstack.strate.fullstack.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ApiResponseFinal {
    public ResponseEntity<Object> buildApiResponse(String message, boolean success, HttpStatus code, boolean error, Object data) {
        ApiResponse response = new ApiResponse(message, success, code.value(), error, data);
        return ResponseEntity.status(code).body(response);
    }
}
