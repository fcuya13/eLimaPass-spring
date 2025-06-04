package com.francocuya13.elimapassspring.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;


@Table
@Getter
@Setter
@Entity
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, name = "fecha_hora")
    private LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "id_tarifa_id", nullable = false)
    private Tarifa tarifa;

    @ManyToOne
    @JoinColumn(name = "codigo_tarjeta_id", nullable = false)
    private Tarjeta tarjeta;

    @Column(nullable = false, name="precio_final")
    private double precioFinal;

}
