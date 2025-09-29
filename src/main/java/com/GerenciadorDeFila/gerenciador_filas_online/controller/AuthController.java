package com.GerenciadorDeFila.gerenciador_filas_online.controller;

import com.GerenciadorDeFila.gerenciador_filas_online.dto.LoginRequestDTO;
import com.GerenciadorDeFila.gerenciador_filas_online.dto.LoginResponseDTO;
import com.GerenciadorDeFila.gerenciador_filas_online.dto.RegistroUsuarioDTO;
import com.GerenciadorDeFila.gerenciador_filas_online.model.usuario.UserRole;
import com.GerenciadorDeFila.gerenciador_filas_online.model.usuario.Usuario;
import com.GerenciadorDeFila.gerenciador_filas_online.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody LoginRequestDTO data) {

        try {
            var usernamePassWord = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getSenha());
            var auth = this.authenticationManager.authenticate(usernamePassWord);
            return ResponseEntity.ok().build();

        } catch (AuthenticationException e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciais inválidas. Verifique e-mail e senha.");
        }

    }

    @PostMapping("/registro")
    public ResponseEntity registrarUsuario(@Valid @RequestBody RegistroUsuarioDTO registroDTO) {
        try {
            Usuario usuario = authService.registrarUsuario(registroDTO,UserRole.USER);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Usuário registrado com sucesso! " + usuario.getNome());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro no registro: " + e.getMessage());
        }
    }

    @PostMapping("/registroadmin")

    public ResponseEntity registrarUsuarioAdmin(@Valid @RequestBody RegistroUsuarioDTO registroDTO) {
        try {
            Usuario usuario = authService.registrarUsuario(registroDTO, UserRole.ADMIN);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Usuário registrado com sucesso! " + usuario.getNome());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro no registro: " + e.getMessage());
        }
    }

    @GetMapping("/usuario/{email}")
    public ResponseEntity<?> buscarUsuario(@PathVariable String email) {
        try {
            return authService.buscarUsuarioPorEmail(email)
                    .map(usuario -> ResponseEntity.ok(usuario))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar usuário: " + e.getMessage());
        }
    }
}
