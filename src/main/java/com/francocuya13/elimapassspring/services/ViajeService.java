package com.francocuya13.elimapassspring.services;

import com.francocuya13.elimapassspring.models.Tarifa;
import com.francocuya13.elimapassspring.models.Tarjeta;
import com.francocuya13.elimapassspring.models.Viaje;
import com.francocuya13.elimapassspring.repositories.TarifaRepository;
import com.francocuya13.elimapassspring.repositories.TarjetaRepository;
import com.francocuya13.elimapassspring.repositories.ViajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ViajeService {

    @Autowired
    private ViajeRepository viajeRepository;

    @Autowired
    private TarjetaRepository tarjetaRepository;

    @Autowired
    private TarifaRepository tarifaRepository;

    // Crear un nuevo viaje
    public Viaje createViaje(String codigoTarjeta, String idRuta, double precioFinal) {
        Tarjeta tarjeta = tarjetaRepository.findById(codigoTarjeta).orElseThrow(() -> new RuntimeException("Tarjeta no encontrada"));

        Tarifa tarifa = tarifaRepository.findById(idRuta).orElseThrow(() -> new RuntimeException("Tarifa no encontrada"));

        // Crear el viaje
        Viaje viaje = new Viaje();
        viaje.setFechaHora(String.valueOf(LocalDateTime.now()));
        viaje.setTarifa(tarifa);
        viaje.setTarjeta(tarjeta);
        viaje.setPrecioFinal(precioFinal);

        return viajeRepository.save(viaje);
    }

    public List<Viaje> getViajesByTarjeta(String codigoTarjeta) {
        return viajeRepository.findTop10ByTarjeta_CodigoOrderByFechaHoraDesc(codigoTarjeta);
    }
}
