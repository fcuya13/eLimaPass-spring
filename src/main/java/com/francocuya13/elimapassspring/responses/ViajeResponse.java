package com.francocuya13.elimapassspring.responses;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ViajeResponse {
    private String id;
    private String fechaHora;
    private String ruta;
    private double precioFinal;

    // Constructor
    public ViajeResponse(String id, LocalDateTime fechaHora, String ruta, double precioFinal) {
        this.id = id;
        this.fechaHora = String.valueOf(fechaHora);
        this.ruta = ruta;
        this.precioFinal = precioFinal;
    }
}