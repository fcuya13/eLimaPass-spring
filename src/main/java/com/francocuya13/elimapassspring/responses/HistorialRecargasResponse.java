package com.francocuya13.elimapassspring.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class HistorialRecargasResponse {
    private String codigoTarjeta;
    private List<RecargaHistResponse> recargas;

    public HistorialRecargasResponse(String codigoTarjeta, List<RecargaHistResponse> recargas) {
        this.codigoTarjeta = codigoTarjeta;
        this.recargas = recargas;
    }
}