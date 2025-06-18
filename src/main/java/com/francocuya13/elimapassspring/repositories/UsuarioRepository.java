package com.francocuya13.elimapassspring.repositories;

import com.francocuya13.elimapassspring.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Optional<Usuario> findByDni(String dni);

    Optional<Usuario> findByEmail(String email);
}

