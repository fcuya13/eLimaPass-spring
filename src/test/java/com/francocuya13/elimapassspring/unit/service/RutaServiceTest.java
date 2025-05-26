package com.francocuya13.elimapassspring.unit.service;

import com.francocuya13.elimapassspring.models.Ruta;
import com.francocuya13.elimapassspring.repositories.RutaRepository;
import com.francocuya13.elimapassspring.services.RutaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class RutaServiceTest {

    @Mock
    private RutaRepository rutaRepository;

    @InjectMocks
    private RutaService rutaService;

    private Ruta ruta1;
    private Ruta ruta2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup test data
        ruta1 = new Ruta();
        ruta1.setId("R001");
        ruta1.setNombre("Ruta Express");
        ruta1.setInicio("Terminal Norte");
        ruta1.setFinalDestino("Terminal Sur");
        ruta1.setServicio("Servicio A");

        ruta2 = new Ruta();
        ruta2.setId("R002");
        ruta2.setNombre("Ruta Turística");
        ruta2.setInicio("Centro Histórico");
        ruta2.setFinalDestino("Playa");
        ruta2.setServicio("Servicio B");
    }

    @Test
    public void whenGetRutas_thenReturnAllRutasOrderedByName() {
        // Arrange
        List<Ruta> rutas = Arrays.asList(ruta1, ruta2);
        when(rutaRepository.findAllByOrderByNombreAsc()).thenReturn(rutas);

        // Act
        List<Ruta> result = rutaService.getRutas();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(ruta1, result.get(0));
        assertEquals(ruta2, result.get(1));
    }
}