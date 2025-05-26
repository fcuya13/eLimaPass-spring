package com.francocuya13.elimapassspring.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Table
@Setter
@Getter
@Entity
public class Recarga {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, name="fecha_hora")
    private LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "codigo_tarjeta_id", nullable = false)
    private Tarjeta tarjeta;

    @Column(nullable = false, name="monto_recargado")
    private Double montoRecargado;

    @Column(nullable = false, name="medio_pago")
    private String medioPago;


}
