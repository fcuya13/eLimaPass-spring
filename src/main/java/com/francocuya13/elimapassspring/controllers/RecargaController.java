package com.francocuya13.elimapassspring.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.francocuya13.elimapassspring.models.Recarga;
import com.francocuya13.elimapassspring.models.Tarjeta;
import com.francocuya13.elimapassspring.responses.HistorialRecargasResponse;
import com.francocuya13.elimapassspring.responses.RecargaHistResponse;
import com.francocuya13.elimapassspring.responses.RecargaResponse;
import com.francocuya13.elimapassspring.services.RecargaService;
import com.francocuya13.elimapassspring.services.TarjetaService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping()
public class RecargaController {

    @Autowired
    private TarjetaService tarjetaService;

    @Autowired
    private RecargaService recargaService;

    @PostMapping("/recargar")
    public ResponseEntity<?> recargarTarjeta(@RequestBody RecargaRequest recargaRequest) {
        String codigoTarjeta = recargaRequest.getCodigoTarjeta();
        Double montoRecargado = recargaRequest.getMontoRecargado();
        String medioPago = recargaRequest.getMedioPago();

        Tarjeta tarjeta = tarjetaService.getTarjetaByCodigo(codigoTarjeta);
        if (tarjeta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("La tarjeta no existe"));
        }

        tarjetaService.updateSaldo(tarjeta, montoRecargado);

        Recarga recarga = new Recarga();
        recarga.setTarjeta(tarjeta);
        recarga.setFechaHora(LocalDateTime.now());
        recarga.setMontoRecargado(montoRecargado);
        recarga.setMedioPago(medioPago);
        recargaService.createRecarga(recarga);

        return ResponseEntity.status(HttpStatus.CREATED).body(new RecargaResponse(
                "Recarga realizada con éxito",
                tarjeta.getCodigo(),
                tarjeta.getSaldo(),
                recarga.getId()
        ));
    }

    @GetMapping("tarjetas/{codigoTarjeta}/recargas")
    public ResponseEntity<?> getHistorialRecargas(@PathVariable String codigoTarjeta) {

        Tarjeta tarjeta = tarjetaService.getTarjetaByCodigo(codigoTarjeta);
        if (tarjeta == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("La tarjeta no existe"));
        }

        List<Recarga> recargas = recargaService.getHistorialRecarga(codigoTarjeta);

        List<RecargaHistResponse> listaRecargas = recargas.stream().map(recarga -> new RecargaHistResponse(
                recarga.getId().toString(),
                recarga.getFechaHora().toString(),
                recarga.getMontoRecargado(),
                recarga.getMedioPago()
        )).collect(Collectors.toList());

        return ResponseEntity.ok(new HistorialRecargasResponse(tarjeta.getCodigo(), listaRecargas));
    }

    @Getter
    @Setter
    public static class RecargaRequest {

        @JsonProperty("codigo_tarjeta")
        private String codigoTarjeta;

        @JsonProperty("monto_recargado")
        private Double montoRecargado;

        @JsonProperty("medio_pago")
        private String medioPago;
    }

    @Getter
    public static class ErrorResponse {
        private final String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

    }


}
