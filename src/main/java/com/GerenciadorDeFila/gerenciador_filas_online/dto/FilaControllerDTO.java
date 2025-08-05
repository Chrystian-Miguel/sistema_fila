package com.GerenciadorDeFila.gerenciador_filas_online.dto;

import com.GerenciadorDeFila.gerenciador_filas_online.model.Status;

import java.time.LocalDateTime;


public record FilaControllerDTO (String senha,
                                 String status,
                                 String prioridade,
                                 String setor,
                                 String nomeServico,
                                 LocalDateTime dataDeCriacao,
                                 LocalDateTime dataDeProcessamento,
                                 LocalDateTime dataDeConclusao,
                                 String atendidoPor,
                                 String nomeCaixa



                                ) {
    }

