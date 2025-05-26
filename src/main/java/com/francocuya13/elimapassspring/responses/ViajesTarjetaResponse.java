package com.francocuya13.elimapassspring.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ViajesTarjetaResponse {

    // Getters y Setters
    private String codigoTarjeta;
    private List<ViajeResponse> viajes;

    // Constructor
    public ViajesTarjetaResponse(String codigoTarjeta, List<ViajeResponse> viajes) {
        this.codigoTarjeta = codigoTarjeta;
        this.viajes = viajes;
    }

}
