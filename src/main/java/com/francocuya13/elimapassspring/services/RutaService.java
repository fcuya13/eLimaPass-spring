package com.francocuya13.elimapassspring.services;

import com.francocuya13.elimapassspring.models.Ruta;
import com.francocuya13.elimapassspring.repositories.RutaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RutaService {

    @Autowired
    private RutaRepository rutaRepository;

    public List<Ruta> getRutas() {
        return rutaRepository.findAllByOrderByNombreAsc();
    }
}
