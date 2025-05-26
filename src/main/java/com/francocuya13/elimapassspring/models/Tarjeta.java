package com.francocuya13.elimapassspring.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table
@Getter
@Setter
@Entity
public class Tarjeta {
    @Id
    private String codigo;

    @Column(nullable = false)
    private double saldo;

    @Column(nullable = false)
    private int tipo;

    @ManyToOne
    @JoinColumn(name = "id_usuario_id", nullable = false)
    private Usuario usuario;

    private double limite;

    @Column(name = "fecha_vencimiento")
    private String fechaVencimiento;

}
