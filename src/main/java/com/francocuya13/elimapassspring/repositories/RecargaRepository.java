package com.francocuya13.elimapassspring.repositories;

import com.francocuya13.elimapassspring.models.Recarga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface RecargaRepository extends JpaRepository<Recarga, UUID> {

    List<Recarga> findTop10ByTarjeta_CodigoOrderByFechaHoraDesc(String codigo);
    Recarga findFirstByMedioPago(String medioPago);
}
