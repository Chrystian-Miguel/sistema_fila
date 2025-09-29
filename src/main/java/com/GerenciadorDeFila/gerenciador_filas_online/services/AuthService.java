package com.GerenciadorDeFila.gerenciador_filas_online.services;

import com.GerenciadorDeFila.gerenciador_filas_online.dto.LoginRequestDTO;
import com.GerenciadorDeFila.gerenciador_filas_online.dto.LoginResponseDTO;
import com.GerenciadorDeFila.gerenciador_filas_online.dto.RegistroUsuarioDTO;
import com.GerenciadorDeFila.gerenciador_filas_online.model.usuario.UserRole;
import com.GerenciadorDeFila.gerenciador_filas_online.model.usuario.Usuario;
import com.GerenciadorDeFila.gerenciador_filas_online.repository.UsuarioRepository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

    }

    public Usuario registrarUsuario(RegistroUsuarioDTO registroDTO, UserRole role) {
        if (usuarioRepository.existsByEmail(registroDTO.getEmail())) {
            throw new RuntimeException("Email já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(registroDTO.getNome());
        usuario.setEmail(registroDTO.getEmail());
        usuario.setSenha(passwordEncoder.encode(registroDTO.getSenha()));
        usuario.setAtivo(true);
        usuario.setRole(role);

        return usuarioRepository.save(usuario);
    }

    private String gerarToken(Usuario usuario) {
        // Implementação simples de token - em produção use JWT
        return "token_" + usuario.getId().toString() + "_" + System.currentTimeMillis();
    }

    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmailAndAtivoTrue(email);
    }

}
