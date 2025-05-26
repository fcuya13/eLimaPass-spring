package com.francocuya13.elimapassspring.repositories;

import com.francocuya13.elimapassspring.models.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RutaRepository extends JpaRepository<Ruta, String> {
    List<Ruta> findAllByOrderByNombreAsc();
}
