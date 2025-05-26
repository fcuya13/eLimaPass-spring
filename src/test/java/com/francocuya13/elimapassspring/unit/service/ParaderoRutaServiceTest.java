package com.francocuya13.elimapassspring.unit.service;

import com.francocuya13.elimapassspring.models.Paradero;
import com.francocuya13.elimapassspring.models.ParaderoRuta;
import com.francocuya13.elimapassspring.models.Ruta;
import com.francocuya13.elimapassspring.repositories.ParaderoRutaRepository;
import com.francocuya13.elimapassspring.services.ParaderoRutaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ParaderoRutaServiceTest {

    @Mock
    private ParaderoRutaRepository paraderoRutaRepository;

    @InjectMocks
    private ParaderoRutaService paraderoRutaService;

    private ParaderoRuta paraderoRuta;
    private Paradero paradero;
    private Ruta ruta;
    private final String ID_RUTA = "R001";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup test data
        paradero = new Paradero();
        String ID_PARADERO = "P001";
        paradero.setId(ID_PARADERO);
        paradero.setNombre("Estación Central");
        paradero.setLatitud(BigDecimal.valueOf(12.3456));
        paradero.setLongitud(BigDecimal.valueOf(-12.3456));

        ruta = new Ruta();
        ruta.setId(ID_RUTA);
        ruta.setNombre("Ruta Express");

        paraderoRuta = new ParaderoRuta();
        paraderoRuta.setParadero(paradero);
        paraderoRuta.setRuta(ruta);
        paraderoRuta.setSentidoIda(true);
    }

    @Test
    public void whenCreateParaderoRuta_thenParaderoRutaIsCreated() {
        // Arrange
        when(paraderoRutaRepository.save(any(ParaderoRuta.class))).thenReturn(paraderoRuta);

        // Act
        ParaderoRuta result = paraderoRutaService.createParaderoRuta(paraderoRuta);

        // Assert
        assertNotNull(result);
        assertEquals(paradero, result.getParadero());
        assertEquals(ruta, result.getRuta());
        assertTrue(result.isSentidoIda());
    }

    @Test
    public void whenGetParaderosByRuta_thenReturnParaderos() {
        // Arrange
        List<ParaderoRuta> paraderoRutas = Collections.singletonList(paraderoRuta);
        when(paraderoRutaRepository.findByRuta_Id(ID_RUTA)).thenReturn(paraderoRutas);

        // Act
        List<ParaderoRuta> result = paraderoRutaService.getParaderosByRuta(ID_RUTA);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(paraderoRuta, result.getFirst());
        assertEquals(paradero, result.getFirst().getParadero());
        assertEquals(ruta, result.getFirst().getRuta());
    }
}