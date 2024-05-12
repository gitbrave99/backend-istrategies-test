package com.fullstack.strate.fullstack.controller;

import com.fullstack.strate.fullstack.dto.UsuarioDTO;
import com.fullstack.strate.fullstack.entity.UsuarioEntity;
import com.fullstack.strate.fullstack.service.UsuarioService;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping(value = "/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("")
    public ResponseEntity<?> list(){
        List<UsuarioEntity> list = this.usuarioService.getUsuarios();
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void create(@RequestBody UsuarioDTO usuario) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, usuario.getPassword());
        usuario.setPassword(hash);
        this.usuarioService.create(usuario);
    }

}
