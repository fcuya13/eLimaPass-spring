package com.francocuya13.elimapassspring.repositories;

import com.francocuya13.elimapassspring.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Usuario findByDni(String dni);
    Usuario findByEmail(String email);
}

