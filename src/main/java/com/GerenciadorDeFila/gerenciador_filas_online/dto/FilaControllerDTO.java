package com.GerenciadorDeFila.gerenciador_filas_online.dto;

import com.GerenciadorDeFila.gerenciador_filas_online.model.Status;

import java.time.LocalDateTime;

// Este record representa a estrutura do JSON que você quer retornar.
// Apenas os campos que você precisa na tela.

public record FilaControllerDTO (String senha,String nomeServico,LocalDateTime dataDeConclusao,LocalDateTime fataDeCriacao,String atendidoPor,String setor, String status) {
    }

