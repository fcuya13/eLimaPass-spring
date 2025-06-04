package com.francocuya13.elimapassspring.unit.controller;

import com.francocuya13.elimapassspring.controllers.ViajeController;
import com.francocuya13.elimapassspring.models.Ruta;
import com.francocuya13.elimapassspring.models.Tarifa;
import com.francocuya13.elimapassspring.models.Tarjeta;
import com.francocuya13.elimapassspring.models.Viaje;
import com.francocuya13.elimapassspring.repositories.TarjetaRepository;
import com.francocuya13.elimapassspring.responses.ViajesTarjetaResponse;
import com.francocuya13.elimapassspring.services.ViajeService;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ViajeControllerTest {

    @Mock
    private ViajeService viajeService;

    @Mock
    private TarjetaRepository tarjetaRepository;

    @InjectMocks
    private ViajeController viajeController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void whenCreateViaje_thenReturnCreatedViaje() {
        // Arrange
        String codigoTarjeta = "123456";
        String idRuta = "1";
        double precioFinal = 3.5;
        UUID id = UUID.randomUUID();

        Viaje viaje = new Viaje();
        viaje.setId(id);

        Tarifa tarifa = new Tarifa();
        Ruta ruta = new Ruta();
        ruta.setNombre("Ruta Express");
        tarifa.setRuta(ruta);

        viaje.setTarifa(tarifa);
        viaje.setPrecioFinal(precioFinal);

        when(viajeService.createViaje(codigoTarjeta, idRuta, precioFinal)).thenReturn(viaje);

        // Act
        Viaje result = viajeController.createViaje(codigoTarjeta, idRuta, precioFinal);

        // Assert
        assertEquals(id, result.getId());
        assertEquals(precioFinal, result.getPrecioFinal());
    }

    @Test
    public void whenGetViajesByTarjeta_thenReturnViajesList() {
        // Arrange
        String codigoTarjeta = "123456";

        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setCodigo(codigoTarjeta);
        UUID id = UUID.randomUUID();

        Viaje viaje1 = new Viaje();
        viaje1.setId(id);
        viaje1.setFechaHora(LocalDateTime.now());
        viaje1.setPrecioFinal(3.5);

        Tarifa tarifa1 = new Tarifa();
        Ruta ruta1 = new Ruta();
        ruta1.setNombre("Ruta Express");
        tarifa1.setRuta(ruta1);
        viaje1.setTarifa(tarifa1);

        Viaje viaje2 = new Viaje();
        viaje2.setId(UUID.randomUUID());
        viaje2.setFechaHora(LocalDateTime.now().minusDays(1));
        viaje2.setPrecioFinal(4.0);

        Tarifa tarifa2 = new Tarifa();
        Ruta ruta2 = new Ruta();
        ruta2.setNombre("Ruta Turística");
        tarifa2.setRuta(ruta2);
        viaje2.setTarifa(tarifa2);

        List<Viaje> viajes = Arrays.asList(viaje1, viaje2);

        when(tarjetaRepository.findById(codigoTarjeta)).thenReturn(Optional.of(tarjeta));
        when(viajeService.getViajesByTarjeta(codigoTarjeta)).thenReturn(viajes);

        // Act
        ResponseEntity<?> response = viajeController.getViajesByTarjeta(codigoTarjeta);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(ViajesTarjetaResponse.class, response.getBody());

        ViajesTarjetaResponse viajesResponse = (ViajesTarjetaResponse) response.getBody();
        assertEquals(codigoTarjeta, viajesResponse.getCodigoTarjeta());
        assertEquals(2, viajesResponse.getViajes().size());
        assertEquals(id.toString(), viajesResponse.getViajes().getFirst().getId());
        assertEquals("Ruta Express", viajesResponse.getViajes().getFirst().getRuta());
        assertEquals(3.5, viajesResponse.getViajes().getFirst().getPrecioFinal());
    }

    @Test
    public void whenGetViajesByInvalidTarjeta_thenReturnNotFound() {
        // Arrange
        String codigoTarjeta = "invalid";
        when(tarjetaRepository.findById(codigoTarjeta)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = viajeController.getViajesByTarjeta(codigoTarjeta);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertInstanceOf(ViajeController.ErrorResponse.class, response.getBody());
        ViajeController.ErrorResponse errorResponse = (ViajeController.ErrorResponse) response.getBody();
        assertEquals("Tarjeta no existe", errorResponse.getError());
    }
}