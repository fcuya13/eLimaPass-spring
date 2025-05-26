package com.francocuya13.elimapassspring.unit.service;

import com.francocuya13.elimapassspring.models.Tarjeta;
import com.francocuya13.elimapassspring.repositories.TarjetaRepository;
import com.francocuya13.elimapassspring.services.TarjetaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TarjetaServiceTest {

    @Mock
    private TarjetaRepository tarjetaRepository;

    @InjectMocks
    private TarjetaService tarjetaService;

    private Tarjeta tarjeta;
    private final String CODIGO_TARJETA = "123456";
    private final double SALDO_INICIAL = 50.0;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup test data
        tarjeta = new Tarjeta();
        tarjeta.setCodigo(CODIGO_TARJETA);
        tarjeta.setSaldo(SALDO_INICIAL);
    }

    @Test
    public void whenGetTarjetaByCodigo_withExistingCodigo_thenReturnTarjeta() {
        // Arrange
        when(tarjetaRepository.findById(CODIGO_TARJETA)).thenReturn(Optional.of(tarjeta));

        // Act
        Tarjeta result = tarjetaService.getTarjetaByCodigo(CODIGO_TARJETA);

        // Assert
        assertEquals(tarjeta, result);
        assertEquals(CODIGO_TARJETA, result.getCodigo());
        assertEquals(SALDO_INICIAL, result.getSaldo());
    }

    @Test
    public void whenGetTarjetaByCodigo_withNonExistingCodigo_thenReturnNull() {
        // Arrange
        String nonExistingCodigo = "non-existing";
        when(tarjetaRepository.findById(nonExistingCodigo)).thenReturn(Optional.empty());

        // Act
        Tarjeta result = tarjetaService.getTarjetaByCodigo(nonExistingCodigo);

        // Assert
        assertNull(result);
    }

    @Test
    public void whenUpdateSaldo_thenSaldoIsUpdatedAndSaved() {
        // Arrange
        double montoToAdd = 25.0;
        double expectedSaldo = SALDO_INICIAL + montoToAdd;
        when(tarjetaRepository.save(any(Tarjeta.class))).thenReturn(tarjeta);

        // Act
        tarjetaService.updateSaldo(tarjeta, montoToAdd);

        // Assert
        assertEquals(expectedSaldo, tarjeta.getSaldo());
        verify(tarjetaRepository).save(tarjeta);
    }
}