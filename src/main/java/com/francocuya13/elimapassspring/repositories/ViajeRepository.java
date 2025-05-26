package com.francocuya13.elimapassspring.repositories;

import com.francocuya13.elimapassspring.models.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ViajeRepository extends JpaRepository<Viaje, UUID> {
    List<Viaje> findTop10ByTarjeta_CodigoOrderByFechaHoraDesc(String codigoTarjeta);
}
