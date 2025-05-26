package com.francocuya13.elimapassspring.controllers;

import com.francocuya13.elimapassspring.models.Tarjeta;
import com.francocuya13.elimapassspring.models.Viaje;
import com.francocuya13.elimapassspring.repositories.TarjetaRepository;
import com.francocuya13.elimapassspring.responses.ViajeResponse;
import com.francocuya13.elimapassspring.responses.ViajesTarjetaResponse;
import com.francocuya13.elimapassspring.services.ViajeService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tarjetas")
public class ViajeController {

    @Autowired
    private ViajeService viajeService;

    @Autowired
    private TarjetaRepository tarjetaRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Viaje createViaje(@RequestParam String codigoTarjeta, @RequestParam String idRuta, @RequestParam double precioFinal) {
        return viajeService.createViaje(codigoTarjeta, idRuta, precioFinal);
    }

    @GetMapping("/{codigoTarjeta}/viajes")
    public ResponseEntity<?> getViajesByTarjeta(@PathVariable String codigoTarjeta) {
        try {
            Tarjeta tarjeta = tarjetaRepository.findById(codigoTarjeta)
                    .orElseThrow(() -> new RuntimeException("Tarjeta no existe"));

            List<Viaje> viajes = viajeService.getViajesByTarjeta(codigoTarjeta);

            List<ViajeResponse> listaViajes = viajes.stream().map(viaje -> new ViajeResponse(
                    viaje.getId().toString(),
                    viaje.getFechaHora(),
                    viaje.getTarifa().getRuta().getNombre(),
                    viaje.getPrecioFinal()
            )).collect(Collectors.toList());

            ViajesTarjetaResponse response = new ViajesTarjetaResponse(tarjeta.getCodigo(), listaViajes);

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Tarjeta no existe"));
        }
    }

    @Getter
    @Setter
    public static class ErrorResponse {
        public ErrorResponse(String error) {
            this.error = error;
        }
        private String error;
    }
}


