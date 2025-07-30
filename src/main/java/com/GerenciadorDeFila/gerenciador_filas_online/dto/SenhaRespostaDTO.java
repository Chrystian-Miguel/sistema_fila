package com.GerenciadorDeFila.gerenciador_filas_online.dto;
import com.GerenciadorDeFila.gerenciador_filas_online.model.SenhaChamada;
import java.time.LocalDateTime;
public record SenhaRespostaDTO(
        String senha,
        String status,
        LocalDateTime dataDeCriacao,
        String servico
) {
    // Construtor de conveniência para mapear facilmente da entidade para o DTO.
    public SenhaRespostaDTO(SenhaChamada senha) {
        this(
                senha.getSenha(),
                senha.getStatus().getDescricao(),
                senha.getDataDeCriacao(),
                senha.getServico().getDescricao()
        );
    }
}