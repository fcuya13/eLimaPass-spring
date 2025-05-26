package com.francocuya13.elimapassspring.responses;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ParaderoRutaResponse {
    private Long id;
    private String nombre;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private boolean sentidoIda;

}