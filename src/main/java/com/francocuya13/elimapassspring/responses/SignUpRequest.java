package com.francocuya13.elimapassspring.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {

    private String dni;

    private String nombres;

    private String apellidos;

    private String email;

    private String password;

    private String numTarjeta;
}