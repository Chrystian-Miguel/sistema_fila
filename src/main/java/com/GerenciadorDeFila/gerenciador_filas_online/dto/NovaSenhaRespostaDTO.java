package com.GerenciadorDeFila.gerenciador_filas_online.dto;

import com.GerenciadorDeFila.gerenciador_filas_online.model.SenhaChamada;

import java.time.LocalDateTime;

public record NovaSenhaRespostaDTO(
        String senha,
        String status,
        LocalDateTime dataDeCriacao,
        String servico,
        String Prioridade
) {

    public NovaSenhaRespostaDTO(SenhaChamada senha) {
        this(
                senha.getSenha(),
                senha.getStatus().getDescricao(),
                senha.getDataDeCriacao(),
                senha.getServico().getDescricao(),
                senha.getPrioridade().getDescricao()
        );
    }
}