package com.fullstack.strate.fullstack.service;

import com.fullstack.strate.fullstack.dto.UsuarioDTO;
import com.fullstack.strate.fullstack.dto.UsuarioLoginDTO;
import com.fullstack.strate.fullstack.entity.UsuarioEntity;

import java.util.List;

public interface UsuarioService {

    List<UsuarioEntity> getUsuarios();
    int create(UsuarioDTO us);
    UsuarioDTO login(UsuarioLoginDTO usl);
}
