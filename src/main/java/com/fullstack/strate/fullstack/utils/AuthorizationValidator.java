package com.fullstack.strate.fullstack.utils;

import com.fullstack.strate.fullstack.entity.Token;
import com.fullstack.strate.fullstack.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationValidator {

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    TokenService tokenService;
    @Autowired
    ApiResponseFinal apiResponseFinal;

    public Token isValidAuth(String token){
        if (jwtUtil.isTokenValid(token)) {
            Token tokenFound = this.tokenService.findToken(token);
            if (tokenFound != null) {
                System.out.println("Tokens: " + tokenFound.isAnulado() + " Expired: " + tokenFound.isExpired());
                if (tokenFound.isAnulado() && tokenFound.isExpired()) {
                    System.out.println("expirado y anulado");
                    return null;
                }
                return tokenFound;
            }
        }
        System.out.println("token invalido");
        return null;
    }
}
