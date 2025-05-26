package com.francocuya13.elimapassspring.repositories;

import com.francocuya13.elimapassspring.models.ParaderoRuta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParaderoRutaRepository extends JpaRepository<ParaderoRuta, Long> {
    List<ParaderoRuta> findByRuta_Id(String idRuta);
}

