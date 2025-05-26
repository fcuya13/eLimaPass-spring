package com.francocuya13.elimapassspring.controllers;

import com.francocuya13.elimapassspring.models.Ruta;
import com.francocuya13.elimapassspring.responses.RutaResponse;
import com.francocuya13.elimapassspring.services.RutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.TreeMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rutas")
public class RutaController {

    @Autowired
    private RutaService rutaService;

    @GetMapping
    public ResponseEntity<?> getRutasAgrupadas() {
        List<Ruta> rutas = rutaService.getRutas();

        Map<String, List<Ruta>> rutasPorServicio = rutas.stream()
                .collect(Collectors.groupingBy(Ruta::getServicio, TreeMap::new, Collectors.toList()));

        Map<String, List<RutaResponse>> rutasPorServicioResponse = rutasPorServicio.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(ruta -> new RutaResponse(
                                        ruta.getId(),
                                        ruta.getNombre(),
                                        ruta.getInicio(),
                                        ruta.getFinalDestino()))
                                .collect(Collectors.toList())
                ));

        return ResponseEntity.ok(rutasPorServicioResponse);
    }
}