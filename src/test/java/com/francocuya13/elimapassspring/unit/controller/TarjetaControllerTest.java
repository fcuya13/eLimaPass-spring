package com.francocuya13.elimapassspring.unit.controller;

import com.francocuya13.elimapassspring.controllers.TarjetaController;
import com.francocuya13.elimapassspring.models.Tarjeta;
import com.francocuya13.elimapassspring.responses.SaldoResponse;
import com.francocuya13.elimapassspring.services.TarjetaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class TarjetaControllerTest {

    @Mock
    private TarjetaService tarjetaService;

    @InjectMocks
    private TarjetaController tarjetaController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenGetSaldoTarjetaWithValidCode_thenReturnSaldo() {
        // Arrange
        String codigoTarjeta = "123456";
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setCodigo(codigoTarjeta);
        tarjeta.setSaldo(100.0);

        when(tarjetaService.getTarjetaByCodigo(codigoTarjeta)).thenReturn(tarjeta);

        // Act
        ResponseEntity<?> response = tarjetaController.getSaldoTarjeta(codigoTarjeta);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(SaldoResponse.class, response.getBody());
        SaldoResponse saldoResponse = (SaldoResponse) response.getBody();
        assertEquals(codigoTarjeta, saldoResponse.getCodigoTarjeta());
        assertEquals(100.0, saldoResponse.getSaldo());
    }

    @Test
    public void whenGetSaldoTarjetaWithInvalidCode_thenReturnNotFound() {
        // Arrange
        String codigoTarjeta = "invalid";
        when(tarjetaService.getTarjetaByCodigo(codigoTarjeta)).thenReturn(null);

        // Act
        ResponseEntity<?> response = tarjetaController.getSaldoTarjeta(codigoTarjeta);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertInstanceOf(TarjetaController.ErrorResponse.class, response.getBody());
        TarjetaController.ErrorResponse errorResponse = (TarjetaController.ErrorResponse) response.getBody();
        assertEquals("Tarjeta no existe", errorResponse.getError());
    }
}