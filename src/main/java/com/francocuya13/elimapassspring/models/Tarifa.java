package com.francocuya13.elimapassspring.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table
@Getter
@Setter
@Entity
public class Tarifa {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "id_ruta_id", nullable = false)
    private Ruta ruta;

    @Column(nullable = false, name = "precio_base")
    private double precioBase;

}

