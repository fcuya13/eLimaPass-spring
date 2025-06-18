package com.francocuya13.elimapassspring.unit.service;
import com.francocuya13.elimapassspring.models.Tarjeta;
import com.francocuya13.elimapassspring.models.Usuario;
import com.francocuya13.elimapassspring.repositories.TarjetaRepository;
import com.francocuya13.elimapassspring.repositories.UsuarioRepository;
import com.francocuya13.elimapassspring.responses.SignUpRequest;
import com.francocuya13.elimapassspring.responses.SignUpResponse;
import com.francocuya13.elimapassspring.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private TarjetaRepository tarjetaRepository;

    private UsuarioService usuarioService;

    @Captor
    private ArgumentCaptor<Tarjeta> tarjetaCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuarioService = new UsuarioService(usuarioRepository, tarjetaRepository);
    }

    @Test
    public void registerUser_WithoutTarjeta_ShouldCreateUserAndNewTarjeta() {
        // Arrange
        SignUpRequest request = new SignUpRequest();
        request.setNombres("Test");
        request.setApellidos("User");
        request.setEmail("test@example.com");
        request.setDni("99997777");
        request.setPassword("password123");
        request.setNumTarjeta(null);

        Usuario usuario = new Usuario();
        usuario.setNombres(request.getNombres());
        usuario.setApellidos(request.getApellidos());
        usuario.setEmail(request.getEmail());
        usuario.setDni(request.getDni());
        usuario.setPassword(request.getPassword());
        usuario.setId(UUID.randomUUID());

        when(usuarioRepository.findByDni(request.getDni())).thenReturn(Optional.empty());
        when(usuarioRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Act
        SignUpResponse response = usuarioService.registerUser(request);

        // Assert
        verify(usuarioRepository).save(any(Usuario.class));
        verify(tarjetaRepository).save(tarjetaCaptor.capture());

        Tarjeta savedTarjeta = tarjetaCaptor.getValue();
        assertNotNull(savedTarjeta.getCodigo());
        assertEquals(usuario, savedTarjeta.getUsuario());
        assertEquals(0.0, savedTarjeta.getSaldo());
        assertEquals(0, savedTarjeta.getTipo());

        assertEquals(usuario.getId().toString(), response.getId());
        assertEquals(usuario.getNombres(), response.getNombres());
    }

    @Test
    void registerUser_WithTarjeta_ShouldCreateUserAndAssociateTarjeta() {
        // Arrange
        SignUpRequest request = new SignUpRequest();
        request.setNombres("Test");
        request.setApellidos("User");
        request.setEmail("test@example.com");
        request.setDni("77778888");
        request.setPassword("password123");
        String numeroTarjeta = "1234567890"; // debe ser válido según la lógica
        request.setNumTarjeta(numeroTarjeta);

        Usuario usuario = new Usuario();
        usuario.setNombres(request.getNombres());
        usuario.setApellidos(request.getApellidos());
        usuario.setEmail(request.getEmail());
        usuario.setDni(request.getDni());
        usuario.setPassword(request.getPassword());
        usuario.setId(UUID.randomUUID());

        when(usuarioRepository.findByDni(request.getDni())).thenReturn(Optional.empty());
        when(usuarioRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Act
        SignUpResponse response = usuarioService.registerUser(request);

        // Assert
        verify(usuarioRepository).save(any(Usuario.class));
        verify(tarjetaRepository).save(tarjetaCaptor.capture());

        Tarjeta savedTarjeta = tarjetaCaptor.getValue();
        assertEquals(numeroTarjeta, savedTarjeta.getCodigo());
        assertEquals(usuario, savedTarjeta.getUsuario());
        assertEquals(10.0, savedTarjeta.getSaldo());
        assertEquals(0, savedTarjeta.getTipo());

        assertEquals(usuario.getId().toString(), response.getId());
        assertEquals(usuario.getNombres(), response.getNombres());
    }

    @Test
    public void getUserByDni_ShouldReturnUser() {
        // Arrange
        String dni = "12345678";
        Usuario expectedUsuario = new Usuario();
        expectedUsuario.setDni(dni);

        when(usuarioRepository.findByDni(dni)).thenReturn(Optional.of(expectedUsuario));

        // Act
        Usuario result = usuarioService.getUserByDni(dni);

        // Assert
        assertEquals(expectedUsuario, result);
    }

    @Test
    public void getUserByEmail_ShouldReturnUser() {
        // Arrange
        String email = "test@example.com";
        Usuario expectedUsuario = new Usuario();
        expectedUsuario.setEmail(email);

        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(expectedUsuario));

        // Act
        Usuario result = usuarioService.getUserByEmail(email);

        // Assert
        assertEquals(expectedUsuario, result);
    }
}