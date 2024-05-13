package com.fullstack.strate.fullstack.serviceImplement;

import com.fullstack.strate.fullstack.dto.UsuarioDTO;
import com.fullstack.strate.fullstack.dto.UsuarioLoginDTO;
import com.fullstack.strate.fullstack.entity.UsuarioEntity;
import com.fullstack.strate.fullstack.repository.UsuarioRepository;
import com.fullstack.strate.fullstack.service.UsuarioService;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UsuarioServiceImplement implements UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public List<UsuarioEntity> getUsuarios() {
        return this.usuarioRepository.findAll();
    }

    @Transactional()
    @Override
    public int create(UsuarioDTO us) {
        return this.usuarioRepository.gestionUsuario(us.getUsername(), us.getPassword(), us.getIdUsuarioTipo(), 0, 0);
    }

    @Override
    public UsuarioDTO login(UsuarioLoginDTO usl) {
        UsuarioDTO usuarioLogeado= new UsuarioDTO();
        UsuarioEntity userl = this.usuarioRepository.login(usl.getUsername());

        if (userl == null) {return null;}
        String passwordHashed= userl.getPassword();
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        if (argon2.verify(passwordHashed, usl.getPassword())) {
            System.out.println("compracion ok");
            usuarioLogeado.setUsername(usl.getUsername());
            usuarioLogeado.setIdUsuarioTipo(userl.getTipoUsuario().getIdUsuarioTipo());
            return usuarioLogeado;
        }
        return null;
    }
}