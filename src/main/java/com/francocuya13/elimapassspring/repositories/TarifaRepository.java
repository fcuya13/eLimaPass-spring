package com.francocuya13.elimapassspring.repositories;

import com.francocuya13.elimapassspring.models.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, String> {
}
