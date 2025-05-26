package com.francocuya13.elimapassspring.controllers;

import com.francocuya13.elimapassspring.models.Tarjeta;
import com.francocuya13.elimapassspring.responses.SaldoResponse;
import com.francocuya13.elimapassspring.services.TarjetaService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarjetas")
public class TarjetaController {

    @Autowired
    private TarjetaService tarjetaService;

    @GetMapping("/{codigoTarjeta}/saldo")
    public ResponseEntity<?> getSaldoTarjeta(@PathVariable String codigoTarjeta) {
        Tarjeta tarjeta = tarjetaService.getTarjetaByCodigo(codigoTarjeta);

        if (tarjeta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Tarjeta no existe"));
        }

        return ResponseEntity.ok(new SaldoResponse(tarjeta.getCodigo(), tarjeta.getSaldo()));
    }

    @Getter
    @Setter
    public static class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }
    }
}
