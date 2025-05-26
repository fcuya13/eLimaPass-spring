package com.francocuya13.elimapassspring.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RecargaResponse {

    private String mensaje;
    private String tarjeta;
    private Double saldoActualizado;
    private UUID recargaId;

    public RecargaResponse(String mensaje, String tarjeta, Double saldoActualizado, UUID recargaId) {
        this.mensaje = mensaje;
        this.tarjeta = tarjeta;
        this.saldoActualizado = saldoActualizado;
        this.recargaId = recargaId;
    }

}
