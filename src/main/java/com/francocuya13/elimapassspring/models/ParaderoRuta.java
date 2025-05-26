package com.francocuya13.elimapassspring.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "paraderoruta")
@Getter
@Setter
@Entity
public class ParaderoRuta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_ruta_id", nullable = false)
    private Ruta ruta;

    @ManyToOne
    @JoinColumn(name = "id_paradero_id", nullable = false)
    private Paradero paradero;

    @Column(nullable = false, name = "sentido_ida")
    private boolean sentidoIda;

}
