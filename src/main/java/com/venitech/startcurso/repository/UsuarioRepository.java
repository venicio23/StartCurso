package com.venitech.startcurso.repository;

import com.venitech.startcurso.model.entity.Usuario;
import com.venitech.startcurso.model.enums.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Usuario> findByIdAndTipo(UUID id, TipoUsuario tipo);
}
