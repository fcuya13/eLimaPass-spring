package com.francocuya13.elimapassspring.unit.controller;

import com.francocuya13.elimapassspring.controllers.RutaController;
import com.francocuya13.elimapassspring.models.Ruta;
import com.francocuya13.elimapassspring.responses.RutaResponse;
import com.francocuya13.elimapassspring.services.RutaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class RutaControllerTest {

    @Mock
    private RutaService rutaService;

    @InjectMocks
    private RutaController rutaController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenGetRutasAgrupadas_thenReturnGroupedRoutes() {
        // Arrange
        Ruta ruta1 = new Ruta();
        ruta1.setId("1");
        ruta1.setNombre("Ruta 1");
        ruta1.setInicio("Inicio 1");
        ruta1.setFinalDestino("Final 1");
        ruta1.setServicio("Servicio A");

        Ruta ruta2 = new Ruta();
        ruta2.setId("2");
        ruta2.setNombre("Ruta 2");
        ruta2.setInicio("Inicio 2");
        ruta2.setFinalDestino("Final 2");
        ruta2.setServicio("Servicio B");

        Ruta ruta3 = new Ruta();
        ruta3.setId("3");
        ruta3.setNombre("Ruta 3");
        ruta3.setInicio("Inicio 3");
        ruta3.setFinalDestino("Final 3");
        ruta3.setServicio("Servicio A");

        List<Ruta> rutas = Arrays.asList(ruta1, ruta2, ruta3);
        when(rutaService.getRutas()).thenReturn(rutas);

        // Act
        ResponseEntity<?> response = rutaController.getRutasAgrupadas();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(Map.class, response.getBody());

        @SuppressWarnings("unchecked")
        Map<String, List<RutaResponse>> result = (Map<String, List<RutaResponse>>) response.getBody();
        assertEquals(2, result.size());
        assertTrue(result.containsKey("Servicio A"));
        assertTrue(result.containsKey("Servicio B"));
        assertEquals(2, result.get("Servicio A").size());
        assertEquals(1, result.get("Servicio B").size());
    }
}