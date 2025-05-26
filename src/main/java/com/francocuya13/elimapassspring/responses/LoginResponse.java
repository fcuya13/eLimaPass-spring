package com.francocuya13.elimapassspring.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String id;
    private String tarjeta;
    private Integer tipo;
    public LoginResponse(String id, String tarjeta, Integer tipo) {
        this.id = id;
        this.tarjeta = tarjeta;
        this.tipo = tipo;
    }
}