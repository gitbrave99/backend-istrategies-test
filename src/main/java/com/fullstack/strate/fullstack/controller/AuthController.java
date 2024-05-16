package com.fullstack.strate.fullstack.controller;

import com.fullstack.strate.fullstack.dto.UsuarioDTO;
import com.fullstack.strate.fullstack.dto.UsuarioLoginDTO;
import com.fullstack.strate.fullstack.entity.Token;
import com.fullstack.strate.fullstack.service.TokenService;
import com.fullstack.strate.fullstack.service.UsuarioService;
import com.fullstack.strate.fullstack.utils.ApiResponseFinal;
import com.fullstack.strate.fullstack.utils.AuthorizationValidator;
import com.fullstack.strate.fullstack.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "**")
@RestController
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    ApiResponseFinal apiResponseFinal;

     @Autowired
    AuthorizationValidator authorizationValidator;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioLoginDTO usuario) {
        System.out.println("login "+ usuario.getUsername()+ " pas"+ usuario.getPassword());
        UsuarioDTO usuarioLogueado = usuarioService.login(usuario);
        if (usuarioLogueado != null) {
            Token newToken=new Token();
            String tokenJwt = jwtUtil.create(String.valueOf(usuarioLogueado.getUsername()), String.valueOf(usuarioLogueado.getIdUsuarioTipo()));
            newToken.setToken(tokenJwt);
            newToken.setExpired(false);
            newToken.setAnulado(false);
            Token savedToken= this.tokenService.saveToken(newToken);
            System.out.println("TOKEN SAVED: "+savedToken.toString());
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("usuario", usuarioLogueado.getUsername());
            responseData.put("tipousuario",usuarioLogueado.getIdUsuarioTipo());
            responseData.put("token", tokenJwt);
            return apiResponseFinal.buildApiResponse("Login exitoso",true, HttpStatus.OK, false, responseData);
        }
        return apiResponseFinal.buildApiResponse("No se pudo iniciar sesión ",false, HttpStatus.CONFLICT,
                true, null);
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String jwt) {
        System.out.println("token:  "+ jwt);
        Token tokenValid=authorizationValidator.isValidAuth(jwt);
        if (tokenValid != null) {
            tokenValid=this.tokenService.findToken(jwt);
            System.out.println("token found: "+ tokenValid);
            if (tokenValid != null) {
                tokenValid.setAnulado(true);
                tokenValid.setExpired(true);
                this.tokenService.updateToken(tokenValid);
                return apiResponseFinal.buildApiResponse("logout exitoso", true, HttpStatus.OK,false,null);
            }
        }
        return apiResponseFinal.buildApiResponse("logout fallido", false, HttpStatus.CONFLICT, true, null);
    }

    @GetMapping("/demo")
    public ResponseEntity<?> demo(@RequestHeader("Authorization") String jwt) {
        if (authorizationValidator.isValidAuth(jwt) != null) {
            return apiResponseFinal.buildApiResponse("LISTA RETORNADA", true, HttpStatus.OK, true, null);
        }
        return apiResponseFinal.buildApiResponse("token invalido o expirado", false, HttpStatus.UNAUTHORIZED, true, null);
    }
}
