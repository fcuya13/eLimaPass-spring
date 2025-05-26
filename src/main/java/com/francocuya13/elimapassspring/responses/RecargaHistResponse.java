package com.francocuya13.elimapassspring.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecargaHistResponse {
    private String id;
    private String fecha;
    private Double montoRecargado;
    private String medioPago;

    public RecargaHistResponse(String id, String fecha, Double montoRecargado, String medioPago) {
        this.id = id;
        this.fecha = fecha;
        this.montoRecargado = montoRecargado;
        this.medioPago = medioPago;
    }
}