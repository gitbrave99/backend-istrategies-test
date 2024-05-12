package com.fullstack.strate.fullstack.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
        private String message;
        private boolean success;
        private int code;
        private boolean error;
        private Object data;
}
