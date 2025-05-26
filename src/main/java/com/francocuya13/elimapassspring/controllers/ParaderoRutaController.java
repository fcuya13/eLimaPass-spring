package com.francocuya13.elimapassspring.controllers;

import com.francocuya13.elimapassspring.models.ParaderoRuta;
import com.francocuya13.elimapassspring.responses.ParaderoRutaResponse;
import com.francocuya13.elimapassspring.services.ParaderoRutaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/paraderos")
public class ParaderoRutaController {

    @Autowired
    private ParaderoRutaService paraderoRutaService;

    @GetMapping("/{idRuta}")
    public List<ParaderoRutaResponse> getParaderosByRuta(@PathVariable String idRuta) {
        List<ParaderoRuta> paraderosRuta = paraderoRutaService.getParaderosByRuta(idRuta);

        List<ParaderoRutaResponse> paraderoRutaResponses = new ArrayList<>();

        for (ParaderoRuta paraderoRuta : paraderosRuta) {
            ParaderoRutaResponse res = new ParaderoRutaResponse();
            res.setId(Long.valueOf(paraderoRuta.getParadero().getId()));
            res.setNombre(paraderoRuta.getParadero().getNombre());
            res.setLatitud(paraderoRuta.getParadero().getLatitud());
            res.setLongitud(paraderoRuta.getParadero().getLongitud());
            res.setSentidoIda(paraderoRuta.isSentidoIda());

            paraderoRutaResponses.add(res);
        }

        return paraderoRutaResponses;

    }



}

