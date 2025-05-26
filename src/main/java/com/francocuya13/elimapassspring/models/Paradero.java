package com.francocuya13.elimapassspring.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Table
@Getter
@Setter
@Entity
public class Paradero {
    @Id
    private String id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, precision = 9, scale = 6)
    private BigDecimal latitud;

    @Column(nullable = false, precision = 9, scale = 6)
    private BigDecimal longitud;

}
