package com.francocuya13.elimapassspring.services;

import com.francocuya13.elimapassspring.models.Recarga;
import com.francocuya13.elimapassspring.repositories.RecargaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecargaService {
    @Autowired
    private RecargaRepository recargaRepository;

    public Recarga createRecarga(Recarga recarga) {
        return recargaRepository.save(recarga);
    }

    public List<Recarga> getHistorialRecarga(String codigoTarjeta) {
        return recargaRepository.findTop10ByTarjeta_CodigoOrderByFechaHoraDesc(codigoTarjeta);
    }
}
