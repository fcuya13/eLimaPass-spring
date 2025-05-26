package com.francocuya13.elimapassspring.unit.controller;

import com.francocuya13.elimapassspring.controllers.RecargaController;
import com.francocuya13.elimapassspring.responses.HistorialRecargasResponse;
import com.francocuya13.elimapassspring.responses.RecargaHistResponse;
import com.francocuya13.elimapassspring.responses.RecargaResponse;
import com.francocuya13.elimapassspring.services.RecargaService;
import com.francocuya13.elimapassspring.services.TarjetaService;
import com.francocuya13.elimapassspring.models.Recarga;
import com.francocuya13.elimapassspring.models.Tarjeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RecargaControllerTest {

    @Mock
    private TarjetaService tarjetaService;

    @Mock
    private RecargaService recargaService;

    @InjectMocks
    private RecargaController recargaController;

    private Tarjeta tarjeta;
    private Recarga recarga;
    private RecargaController.RecargaRequest recargaRequest;
    private final String CODIGO_TARJETA = "123456";
    private final Double MONTO_RECARGA = 50.0;
    private final UUID id = UUID.randomUUID();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        // Setup test data
        tarjeta = new Tarjeta();
        tarjeta.setCodigo(CODIGO_TARJETA);
        tarjeta.setSaldo(100.0);

        recarga = new Recarga();
        recarga.setId(id);
        recarga.setTarjeta(tarjeta);
        recarga.setFechaHora(LocalDateTime.now());
        recarga.setMontoRecargado(MONTO_RECARGA);
        String MEDIO_PAGO = "efectivo";
        recarga.setMedioPago(MEDIO_PAGO);

        recargaRequest = new RecargaController.RecargaRequest();
        recargaRequest.setCodigoTarjeta(CODIGO_TARJETA);
        recargaRequest.setMontoRecargado(MONTO_RECARGA);
        recargaRequest.setMedioPago(MEDIO_PAGO);
    }

    @Test
    public void whenRecargarTarjeta_withValidData_thenSuccess() {
        // Arrange
        when(tarjetaService.getTarjetaByCodigo(CODIGO_TARJETA)).thenReturn(tarjeta);
        when(recargaService.createRecarga(any(Recarga.class))).thenReturn(recarga);

        // Act
        ResponseEntity<?> response = recargaController.recargarTarjeta(recargaRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(tarjetaService).updateSaldo(tarjeta, MONTO_RECARGA);
        verify(recargaService).createRecarga(any(Recarga.class));

        assertInstanceOf(RecargaResponse.class, response.getBody());
        RecargaResponse recargaResponse = (RecargaResponse) response.getBody();
        assertEquals("Recarga realizada con éxito", recargaResponse.getMensaje());
        assertEquals(CODIGO_TARJETA, recargaResponse.getTarjeta());
        assertEquals(tarjeta.getSaldo(), recargaResponse.getSaldoActualizado());
    }

    @Test
    public void whenRecargarTarjeta_withInvalidTarjeta_thenNotFound() {
        // Arrange
        when(tarjetaService.getTarjetaByCodigo(CODIGO_TARJETA)).thenReturn(null);

        // Act
        ResponseEntity<?> response = recargaController.recargarTarjeta(recargaRequest);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertInstanceOf(RecargaController.ErrorResponse.class, response.getBody());
        RecargaController.ErrorResponse errorResponse = (RecargaController.ErrorResponse) response.getBody();
        assertEquals("La tarjeta no existe", errorResponse.getError());
    }

    @Test
    public void whenGetHistorialRecargas_withValidTarjeta_thenReturnHistory() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        List<Recarga> recargas = Arrays.asList(
                createRecarga(uuid, LocalDateTime.now().minusDays(2), 50.0, "efectivo"),
                createRecarga(uuid2, LocalDateTime.now().minusDays(1), 30.0, "tarjeta")
        );

        when(tarjetaService.getTarjetaByCodigo(CODIGO_TARJETA)).thenReturn(tarjeta);
        when(recargaService.getHistorialRecarga(CODIGO_TARJETA)).thenReturn(recargas);

        // Act
        ResponseEntity<?> response = recargaController.getHistorialRecargas(CODIGO_TARJETA);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(HistorialRecargasResponse.class, response.getBody());

        HistorialRecargasResponse historialResponse = (HistorialRecargasResponse) response.getBody();
        assertEquals(CODIGO_TARJETA, historialResponse.getCodigoTarjeta());
        assertEquals(2, historialResponse.getRecargas().size());

        RecargaHistResponse firstRecarga = historialResponse.getRecargas().getFirst();
        assertEquals(uuid.toString(), firstRecarga.getId());
        assertEquals(50.0, firstRecarga.getMontoRecargado());
        assertEquals("efectivo", firstRecarga.getMedioPago());

        RecargaHistResponse secondRecarga = historialResponse.getRecargas().get(1);
        assertEquals(uuid2.toString(), secondRecarga.getId());
        assertEquals(30.0, secondRecarga.getMontoRecargado());
        assertEquals("tarjeta", secondRecarga.getMedioPago());
    }

    @Test
    public void whenGetHistorialRecargas_withInvalidTarjeta_thenNotFound() {
        // Arrange
        when(tarjetaService.getTarjetaByCodigo(CODIGO_TARJETA)).thenReturn(null);

        // Act
        ResponseEntity<?> response = recargaController.getHistorialRecargas(CODIGO_TARJETA);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertInstanceOf(RecargaController.ErrorResponse.class, response.getBody());
        RecargaController.ErrorResponse errorResponse = (RecargaController.ErrorResponse) response.getBody();
        assertEquals("La tarjeta no existe", errorResponse.getError());
    }

    private Recarga createRecarga(UUID id, LocalDateTime dateTime, Double amount, String paymentMethod) {
        Recarga recarga = new Recarga();
        recarga.setId(id);
        recarga.setTarjeta(tarjeta);
        recarga.setFechaHora(dateTime);
        recarga.setMontoRecargado(amount);
        recarga.setMedioPago(paymentMethod);
        return recarga;
    }
}