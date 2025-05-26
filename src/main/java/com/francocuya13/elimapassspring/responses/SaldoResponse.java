package com.francocuya13.elimapassspring.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaldoResponse {
    private String codigoTarjeta;
    private Double saldo;

    public SaldoResponse(String codigoTarjeta, Double saldo) {
        this.codigoTarjeta = codigoTarjeta;
        this.saldo = saldo;
    }
}
