package com.fullstack.strate.fullstack.repository;

import com.fullstack.strate.fullstack.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer> {


    @Modifying
    @Query(value = "{CALL gestion_usuario(:pUsername, :pPassword, :pIdTipoUsuario, :pIdUsuario, :pOpcion)}", nativeQuery = true)
    int gestionUsuario(
            @Param("pUsername") String pUsername,@Param("pPassword") String pPassword,
        @Param("pIdTipoUsuario") Integer pIdTipoUsuario, @Param("pIdUsuario") Integer pIdUsuario,@Param("pOpcion") Integer pOpcion);

    @Query(value = "select p from UsuarioEntity p where p.username = :username")
    UsuarioEntity login(@Param("username")String username);

    UsuarioEntity findUsuarioEntityByUsername(String username);
    @Query("SELECT u FROM UsuarioEntity u WHERE u.username = :username")
    UsuarioEntity findByUsername(String username);
}
