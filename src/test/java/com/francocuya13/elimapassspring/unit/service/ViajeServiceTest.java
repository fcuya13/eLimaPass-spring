package com.francocuya13.elimapassspring.unit.service;

import com.francocuya13.elimapassspring.models.Ruta;
import com.francocuya13.elimapassspring.models.Tarifa;
import com.francocuya13.elimapassspring.models.Tarjeta;
import com.francocuya13.elimapassspring.models.Viaje;
import com.francocuya13.elimapassspring.repositories.TarifaRepository;
import com.francocuya13.elimapassspring.repositories.TarjetaRepository;
import com.francocuya13.elimapassspring.repositories.ViajeRepository;
import com.francocuya13.elimapassspring.services.ViajeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ViajeServiceTest {

    @Mock
    private ViajeRepository viajeRepository;

    @Mock
    private TarjetaRepository tarjetaRepository;

    @Mock
    private TarifaRepository tarifaRepository;

    @InjectMocks
    private ViajeService viajeService;

    private Tarjeta tarjeta;
    private Tarifa tarifa;
    private Viaje viaje;
    private final String CODIGO_TARJETA = "123456";
    private final String ID_RUTA = "R001";
    private final double PRECIO_FINAL = 3.5;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup test data
        tarjeta = new Tarjeta();
        tarjeta.setCodigo(CODIGO_TARJETA);
        tarjeta.setSaldo(100.0);

        tarifa = new Tarifa();
        tarifa.setId(ID_RUTA);

        Ruta ruta = new Ruta();
        ruta.setId(ID_RUTA);
        ruta.setNombre("Ruta Express");
        tarifa.setRuta(ruta);

        viaje = new Viaje();
        viaje.setFechaHora(String.valueOf(LocalDateTime.now()));
        viaje.setTarjeta(tarjeta);
        viaje.setTarifa(tarifa);
        viaje.setPrecioFinal(PRECIO_FINAL);
    }

    @Test
    public void whenCreateViaje_thenViajeIsCreated() {
        // Arrange
        when(tarjetaRepository.findById(CODIGO_TARJETA)).thenReturn(Optional.of(tarjeta));
        when(tarifaRepository.findById(ID_RUTA)).thenReturn(Optional.of(tarifa));
        when(viajeRepository.save(any(Viaje.class))).thenReturn(viaje);

        // Act
        Viaje result = viajeService.createViaje(CODIGO_TARJETA, ID_RUTA, PRECIO_FINAL);

        // Assert
        assertNotNull(result);
        assertEquals(tarjeta, result.getTarjeta());
        assertEquals(tarifa, result.getTarifa());
        assertEquals(PRECIO_FINAL, result.getPrecioFinal());
    }

    @Test
    public void whenCreateViaje_withInvalidTarjeta_thenThrowException() {
        // Arrange
        when(tarjetaRepository.findById(CODIGO_TARJETA)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> viajeService.createViaje(CODIGO_TARJETA, ID_RUTA, PRECIO_FINAL)
        );

        assertEquals("Tarjeta no encontrada", exception.getMessage());
    }

    @Test
    public void whenCreateViaje_withInvalidTarifa_thenThrowException() {
        // Arrange
        when(tarjetaRepository.findById(CODIGO_TARJETA)).thenReturn(Optional.of(tarjeta));
        when(tarifaRepository.findById(ID_RUTA)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> viajeService.createViaje(CODIGO_TARJETA, ID_RUTA, PRECIO_FINAL)
        );

        assertEquals("Tarifa no encontrada", exception.getMessage());
    }

    @Test
    public void whenGetViajesByTarjeta_thenReturnViajes() {
        // Arrange
        List<Viaje> viajesList = Collections.singletonList(viaje);
        when(viajeRepository.findTop10ByTarjeta_CodigoOrderByFechaHoraDesc(CODIGO_TARJETA)).thenReturn(viajesList);

        // Act
        List<Viaje> result = viajeService.getViajesByTarjeta(CODIGO_TARJETA);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(viaje, result.getFirst());
    }
}