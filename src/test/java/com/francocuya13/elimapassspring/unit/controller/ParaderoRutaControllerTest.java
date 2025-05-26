package com.francocuya13.elimapassspring.unit.controller;

import com.francocuya13.elimapassspring.controllers.ParaderoRutaController;
import com.francocuya13.elimapassspring.models.Paradero;
import com.francocuya13.elimapassspring.models.ParaderoRuta;
import com.francocuya13.elimapassspring.responses.ParaderoRutaResponse;
import com.francocuya13.elimapassspring.services.ParaderoRutaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ParaderoRutaControllerTest {

    @Mock
    private ParaderoRutaService paraderoRutaService;

    @InjectMocks
    private ParaderoRutaController paraderoRutaController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenGetParaderosByRuta_thenReturnParaderos() {
        // Arrange
        String idRuta = "1";

        Paradero paradero1 = new Paradero();
        paradero1.setId("1");
        paradero1.setNombre("Paradero 1");
        paradero1.setLatitud(BigDecimal.valueOf(12.345));
        paradero1.setLongitud(BigDecimal.valueOf(-12.345));

        Paradero paradero2 = new Paradero();
        paradero2.setId("2");
        paradero2.setNombre("Paradero 2");
        paradero2.setLatitud(BigDecimal.valueOf(23.456));
        paradero2.setLongitud(BigDecimal.valueOf(-23.456));

        ParaderoRuta paraderoRuta1 = new ParaderoRuta();
        paraderoRuta1.setParadero(paradero1);
        paraderoRuta1.setSentidoIda(true);

        ParaderoRuta paraderoRuta2 = new ParaderoRuta();
        paraderoRuta2.setParadero(paradero2);
        paraderoRuta2.setSentidoIda(false);

        List<ParaderoRuta> paraderoRutas = Arrays.asList(paraderoRuta1, paraderoRuta2);
        when(paraderoRutaService.getParaderosByRuta(idRuta)).thenReturn(paraderoRutas);

        // Act
        List<ParaderoRutaResponse> response = paraderoRutaController.getParaderosByRuta(idRuta);

        // Assert
        assertEquals(2, response.size());

        assertEquals(1L, response.getFirst().getId());
        assertEquals("Paradero 1", response.getFirst().getNombre());
        assertEquals(12.345, response.getFirst().getLatitud().doubleValue(), 0.001);
        assertEquals(-12.345, response.getFirst().getLongitud().doubleValue(), 0.001);
        assertTrue(response.getFirst().isSentidoIda());

        assertEquals(2L, response.get(1).getId());
        assertEquals("Paradero 2", response.get(1).getNombre());
        assertEquals(23.456, response.get(1).getLatitud().doubleValue(), 0.001);
        assertEquals(-23.456, response.get(1).getLongitud().doubleValue(), 0.001);
        assertFalse(response.get(1).isSentidoIda());
    }
}