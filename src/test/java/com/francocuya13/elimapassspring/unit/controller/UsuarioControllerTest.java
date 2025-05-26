package com.francocuya13.elimapassspring.unit.controller;

import com.francocuya13.elimapassspring.controllers.UsuarioController;
import com.francocuya13.elimapassspring.models.Tarjeta;
import com.francocuya13.elimapassspring.models.Usuario;
import com.francocuya13.elimapassspring.repositories.TarjetaRepository;
import com.francocuya13.elimapassspring.responses.LoginResponse;
import com.francocuya13.elimapassspring.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UsuarioControllerTest {

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private TarjetaRepository tarjetaRepository;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_ShouldCallServiceAndReturnCreatedUser() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setNombres("Test");
        usuario.setApellidos("User");
        usuario.setEmail("test@example.com");
        usuario.setDni("12345678");
        usuario.setPassword("password123");

        String numTarjeta = "123456789";

        when(usuarioService.createUser(any(Usuario.class), eq(numTarjeta))).thenReturn(usuario);

        // Act
        Usuario result = usuarioController.createUser(usuario, numTarjeta);

        // Assert
        assertEquals(usuario, result);
        verify(usuarioService).createUser(usuario, numTarjeta);
    }

    @Test
    void login_WithValidCredentials_ShouldReturnOkAndLoginResponse() {
        // Arrange
        UsuarioController.LoginRequest loginRequest = new UsuarioController.LoginRequest();
        loginRequest.setDni("12345678");
        loginRequest.setPassword("password123");

        Usuario usuario = new Usuario();
        UUID userId = UUID.randomUUID();
        usuario.setId(userId);
        usuario.setDni("12345678");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode("password123");
        usuario.setPassword(encodedPassword);

        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setCodigo("123456789");
        tarjeta.setTipo(0);

        when(usuarioService.getUserByDni("12345678")).thenReturn(usuario);
        when(tarjetaRepository.findByUsuario(usuario)).thenReturn(tarjeta);

        // Act
        ResponseEntity<?> response = usuarioController.login(loginRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertInstanceOf(LoginResponse.class, response.getBody());

        LoginResponse loginResponse = (LoginResponse) response.getBody();
        assertEquals(userId.toString(), loginResponse.getId());
        assertEquals("123456789", loginResponse.getTarjeta());
        assertEquals(0, loginResponse.getTipo());
    }

    @Test
    void login_WithInvalidCredentials_ShouldReturnUnauthorized() {
        // Arrange
        UsuarioController.LoginRequest loginRequest = new UsuarioController.LoginRequest();
        loginRequest.setDni("12345678");
        loginRequest.setPassword("wrong_password");

        Usuario usuario = new Usuario();
        usuario.setDni("12345678");
        usuario.setPassword("encoded_password");

        when(usuarioService.getUserByDni("12345678")).thenReturn(usuario);

        // Mockeamos checkPassword para que retorne false

            UsuarioController spyController = spy(usuarioController);
            doReturn(false).when(spyController).checkPassword(anyString(), anyString());

            // Act
            ResponseEntity<?> response = spyController.login(loginRequest);

            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
            assertInstanceOf(UsuarioController.ErrorResponse.class, response.getBody());

            // Act
            response = usuarioController.login(loginRequest);

            // Assert
            assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
         assertInstanceOf(UsuarioController.ErrorResponse.class, response.getBody());

    }

    @Test
    void login_WithNonExistentUser_ShouldReturnUnauthorized() {
        // Arrange
        UsuarioController.LoginRequest loginRequest = new UsuarioController.LoginRequest();
        loginRequest.setDni("nonexistent");
        loginRequest.setPassword("password123");

        when(usuarioService.getUserByDni("nonexistent")).thenReturn(null);

        // Act
        ResponseEntity<?> response = usuarioController.login(loginRequest);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertInstanceOf(UsuarioController.ErrorResponse.class, response.getBody());
        UsuarioController.ErrorResponse errorResponse = (UsuarioController.ErrorResponse) response.getBody();
        assertEquals("Credenciales incorrectas", errorResponse.getError());
    }
}