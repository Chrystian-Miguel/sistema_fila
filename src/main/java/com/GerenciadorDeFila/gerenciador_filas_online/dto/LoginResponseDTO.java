package com.GerenciadorDeFila.gerenciador_filas_online.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    
    private String id;
    private String nome;
    private String email;
    private String token;
    private String tipoToken = "Bearer";
    
    public LoginResponseDTO(String id, String nome, String email, String token) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.token = token;
    }
}
