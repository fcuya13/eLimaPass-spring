package com.francocuya13.elimapassspring.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponse {
    private String id;
    private String nombres;

    public SignUpResponse(String id, String nombres) {
        this.id = id;
        this.nombres = nombres;
    }
}