package com.francocuya13.elimapassspring.controllers;

import com.francocuya13.elimapassspring.models.Tarjeta;
import com.francocuya13.elimapassspring.models.Usuario;
import com.francocuya13.elimapassspring.repositories.TarjetaRepository;
import com.francocuya13.elimapassspring.responses.LoginResponse;
import com.francocuya13.elimapassspring.services.UsuarioService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private TarjetaRepository tarjetaRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario createUser(@RequestBody Usuario usuario, @RequestParam(required = false) String numTarjeta) {
        return usuarioService.createUser(usuario, numTarjeta);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Usuario usuario = usuarioService.getUserByDni(loginRequest.getDni());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode(loginRequest.getPassword()));
        if (usuario != null && checkPassword(loginRequest.getPassword(), usuario.getPassword())) {
            Tarjeta tarjeta = tarjetaRepository.findByUsuario(usuario);

            return ResponseEntity.ok(new LoginResponse(usuario.getId().toString(), tarjeta.getCodigo(), tarjeta.getTipo()));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("Credenciales incorrectas"));
        }
    }

    public boolean checkPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode(rawPassword));
        return encoder.matches(rawPassword, encodedPassword);
    }

    @Getter
    @Setter
    public static class LoginRequest {
        private String dni;
        private String password;
    }

    @Getter
    @Setter
    public static class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }
    }
}
