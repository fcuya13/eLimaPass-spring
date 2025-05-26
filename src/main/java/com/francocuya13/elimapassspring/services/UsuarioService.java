package com.francocuya13.elimapassspring.services;

import com.francocuya13.elimapassspring.models.Tarjeta;
import com.francocuya13.elimapassspring.models.Usuario;
import com.francocuya13.elimapassspring.repositories.TarjetaRepository;
import com.francocuya13.elimapassspring.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TarjetaRepository tarjetaRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Crear un nuevo usuario y asociarle una tarjeta
    @Transactional
    public Usuario createUser(Usuario usuario, String numeroTarjeta) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); // Encriptar contraseña
        Usuario savedUser = usuarioRepository.save(usuario);

        if (numeroTarjeta == null) {
            // Si no se proporciona una tarjeta, se crea una nueva tarjeta con saldo 0
            Tarjeta tarjeta = new Tarjeta();
            tarjeta.setCodigo(String.valueOf((int) (Math.random() * 1000000000))); // Generar código de tarjeta aleatorio
            tarjeta.setUsuario(savedUser);
            tarjeta.setSaldo(0);
            tarjeta.setTipo(0); // Tipo 0 (puedes cambiar la lógica según tus necesidades)
            tarjeta.setLimite(0);
            tarjetaRepository.save(tarjeta);
        } else {
            // Si se proporciona un número de tarjeta, lo asociamos al usuario
            Tarjeta tarjeta = new Tarjeta();
            tarjeta.setCodigo(numeroTarjeta);
            tarjeta.setUsuario(savedUser);
            tarjeta.setSaldo(10); // Saldo inicial de 10
            tarjeta.setTipo(0);
            tarjeta.setLimite(0);
            tarjetaRepository.save(tarjeta);
        }

        return savedUser;
    }

    public Usuario getUserByDni(String dni) {
        return usuarioRepository.findByDni(dni);
    }

    public Usuario getUserByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
}
