package com.francocuya13.elimapassspring.unit.service;
import com.francocuya13.elimapassspring.models.Tarjeta;
import com.francocuya13.elimapassspring.models.Usuario;
import com.francocuya13.elimapassspring.repositories.TarjetaRepository;
import com.francocuya13.elimapassspring.repositories.UsuarioRepository;
import com.francocuya13.elimapassspring.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private TarjetaRepository tarjetaRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @Captor
    private ArgumentCaptor<Tarjeta> tarjetaCaptor;

    @Test
    public void createUser_WithoutTarjeta_ShouldCreateUserAndNewTarjeta() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setNombres("Test");
        usuario.setApellidos("User");
        usuario.setEmail("test@example.com");
        usuario.setDni("12345678");
        usuario.setPassword("password123");

        UUID userId = UUID.randomUUID();
        usuario.setId(userId);

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Act
        usuarioService.createUser(usuario, null);

        // Assert
        verify(usuarioRepository).save(usuario);
        verify(tarjetaRepository).save(tarjetaCaptor.capture());

        Tarjeta savedTarjeta = tarjetaCaptor.getValue();
        assertNotNull(savedTarjeta.getCodigo());
        assertEquals(usuario, savedTarjeta.getUsuario());
        assertEquals(0, savedTarjeta.getSaldo());
        assertEquals(0, savedTarjeta.getTipo());
    }

    @Test
    public void createUser_WithTarjeta_ShouldCreateUserAndAssociateTarjeta() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setNombres("Test");
        usuario.setApellidos("User");
        usuario.setEmail("test@example.com");
        usuario.setDni("12345678");
        usuario.setPassword("password123");

        UUID userId = UUID.randomUUID();
        usuario.setId(userId);

        String numeroTarjeta = "123456789";

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        // Act
        usuarioService.createUser(usuario, numeroTarjeta);

        // Assert
        verify(usuarioRepository).save(usuario);
        verify(tarjetaRepository).save(tarjetaCaptor.capture());

        Tarjeta savedTarjeta = tarjetaCaptor.getValue();
        assertEquals(numeroTarjeta, savedTarjeta.getCodigo());
        assertEquals(usuario, savedTarjeta.getUsuario());
        assertEquals(10, savedTarjeta.getSaldo());
        assertEquals(0, savedTarjeta.getTipo());
    }

    @Test
    public void getUserByDni_ShouldReturnUser() {
        // Arrange
        String dni = "12345678";
        Usuario expectedUsuario = new Usuario();
        expectedUsuario.setDni(dni);

        when(usuarioRepository.findByDni(dni)).thenReturn(expectedUsuario);

        // Act
        Usuario result = usuarioService.getUserByDni(dni);

        // Assert
        assertEquals(expectedUsuario, result);
        verify(usuarioRepository).findByDni(dni);
    }

    @Test
    public void getUserByEmail_ShouldReturnUser() {
        // Arrange
        String email = "test@example.com";
        Usuario expectedUsuario = new Usuario();
        expectedUsuario.setEmail(email);

        when(usuarioRepository.findByEmail(email)).thenReturn(expectedUsuario);

        // Act
        Usuario result = usuarioService.getUserByEmail(email);

        // Assert
        assertEquals(expectedUsuario, result);
        verify(usuarioRepository).findByEmail(email);
    }
}