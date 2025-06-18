package com.francocuya13.elimapassspring.services;

import com.francocuya13.elimapassspring.models.Tarjeta;
import com.francocuya13.elimapassspring.models.Usuario;
import com.francocuya13.elimapassspring.repositories.TarjetaRepository;
import com.francocuya13.elimapassspring.repositories.UsuarioRepository;
import com.francocuya13.elimapassspring.responses.SignUpRequest;
import com.francocuya13.elimapassspring.responses.SignUpResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final TarjetaRepository tarjetaRepository;

    private final Random random = new Random();
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UsuarioService(UsuarioRepository usuarioRepository, TarjetaRepository tarjetaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.tarjetaRepository = tarjetaRepository;
    }

    @Transactional
    public SignUpResponse registerUser(SignUpRequest request) {
        if (usuarioRepository.findByDni(request.getDni()).isPresent()) {
            throw new IllegalArgumentException("DNI ya registrado");
        }
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email ya registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setDni(request.getDni());
        usuario.setNombres(request.getNombres());
        usuario.setApellidos(request.getApellidos());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuario = usuarioRepository.save(usuario);

        String numTarjeta = request.getNumTarjeta();
        Tarjeta tarjeta = new Tarjeta();

        if (numTarjeta == null || numTarjeta.isEmpty()) {
            String codigo = String.valueOf(1000000000L + random.nextInt(900000000));
            tarjeta.setCodigo(codigo);
            tarjeta.setSaldo(0.0);
        } else {
            if (numTarjeta.length() != 10) {
                throw new IllegalArgumentException("Numero de tarjeta invalida");
            }
            tarjeta.setCodigo(numTarjeta);
            tarjeta.setSaldo(10.0);
        }
        tarjeta.setTipo(0);
        tarjeta.setLimite(0.0);
        tarjeta.setUsuario(usuario);
        tarjetaRepository.save(tarjeta);

        return new SignUpResponse(usuario.getId().toString(), usuario.getNombres());
    }

    public Usuario getUserByDni(String dni) {
        return usuarioRepository.findByDni(dni).isPresent() ? usuarioRepository.findByDni(dni).get() : null;
    }

    public Usuario getUserByEmail(String email) {
        return usuarioRepository.findByEmail(email).isPresent() ? usuarioRepository.findByEmail(email).get() : null;
    }
}

