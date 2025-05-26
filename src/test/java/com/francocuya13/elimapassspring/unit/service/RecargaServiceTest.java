package com.francocuya13.elimapassspring.unit.service;

import com.francocuya13.elimapassspring.models.Recarga;
import com.francocuya13.elimapassspring.models.Tarjeta;
import com.francocuya13.elimapassspring.repositories.RecargaRepository;
import com.francocuya13.elimapassspring.services.RecargaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class RecargaServiceTest {

    @Mock
    private RecargaRepository recargaRepository;

    @InjectMocks
    private RecargaService recargaService;

    private Recarga recarga;
    private Tarjeta tarjeta;
    private final String CODIGO_TARJETA = "123456";
    private final double MONTO_RECARGA = 50.0;
    private final String MEDIO_PAGO = "efectivo";
    private final UUID uuid = UUID.randomUUID();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup test data
        tarjeta = new Tarjeta();
        tarjeta.setCodigo(CODIGO_TARJETA);
        tarjeta.setSaldo(100.0);

        recarga = new Recarga();
        recarga.setId(uuid);
        recarga.setTarjeta(tarjeta);
        recarga.setFechaHora(LocalDateTime.now());
        recarga.setMontoRecargado(MONTO_RECARGA);
        recarga.setMedioPago(MEDIO_PAGO);
    }

    @Test
    public void whenCreateRecarga_thenRecargaIsCreated() {
        // Arrange
        when(recargaRepository.save(any(Recarga.class))).thenReturn(recarga);

        // Act
        Recarga result = recargaService.createRecarga(recarga);

        // Assert
        assertNotNull(result);
        assertEquals(uuid, result.getId());
        assertEquals(tarjeta, result.getTarjeta());
        assertEquals(MONTO_RECARGA, result.getMontoRecargado());
        assertEquals(MEDIO_PAGO, result.getMedioPago());
    }

    @Test
    public void whenGetHistorialRecarga_thenReturnRecargas() {
        // Arrange
        List<Recarga> recargasList = Collections.singletonList(recarga);
        when(recargaRepository.findTop10ByTarjeta_CodigoOrderByFechaHoraDesc(CODIGO_TARJETA)).thenReturn(recargasList);

        // Act
        List<Recarga> result = recargaService.getHistorialRecarga(CODIGO_TARJETA);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(recarga, result.getFirst());
    }
}