package com.francocuya13.elimapassspring.services;

import com.francocuya13.elimapassspring.models.Tarjeta;
import com.francocuya13.elimapassspring.repositories.TarjetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TarjetaService {

    @Autowired
    private TarjetaRepository tarjetaRepository;

    public Tarjeta getTarjetaByCodigo(String codigo) {
        return tarjetaRepository.findById(codigo).orElse(null);
    }

    public void updateSaldo(Tarjeta tarjeta, double monto) {
        tarjeta.setSaldo(tarjeta.getSaldo() + monto);
        tarjetaRepository.save(tarjeta);
    }

}
