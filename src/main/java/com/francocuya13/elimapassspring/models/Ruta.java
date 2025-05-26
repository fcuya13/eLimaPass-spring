package com.francocuya13.elimapassspring.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table
@Getter
@Setter
@Entity
public class Ruta {
    @Id
    private String id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String servicio;

    @Column(nullable = false)
    private String inicio;

    @Column(nullable = false, name="final")
    private String finalDestino;
}
