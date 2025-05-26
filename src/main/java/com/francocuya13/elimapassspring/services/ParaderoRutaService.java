package com.francocuya13.elimapassspring.services;

import com.francocuya13.elimapassspring.models.ParaderoRuta;
import com.francocuya13.elimapassspring.repositories.ParaderoRutaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParaderoRutaService {

    @Autowired
    private ParaderoRutaRepository paraderoRutaRepository;

    // Crear una nueva relación entre un paradero y una ruta
    public ParaderoRuta createParaderoRuta(ParaderoRuta paraderoRuta) {
        return paraderoRutaRepository.save(paraderoRuta);
    }

    // Obtener todos los paraderos asociados a una ruta
    public List<ParaderoRuta> getParaderosByRuta(String idRuta) {
        return paraderoRutaRepository.findByRuta_Id(idRuta);
    }
}

