package com.francocuya13.elimapassspring.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RutaResponse {
    private String id;
    private String nombre;
    private String inicio;
    private String finalDestino;

    public RutaResponse(String id, String nombre, String inicio, String finalDestino) {
        this.id = id;
        this.nombre = nombre;
        this.inicio = inicio;
        this.finalDestino = finalDestino;
    }
}