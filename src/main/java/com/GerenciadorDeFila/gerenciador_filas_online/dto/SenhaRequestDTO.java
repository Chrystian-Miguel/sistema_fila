package com.GerenciadorDeFila.gerenciador_filas_online.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

// Usando Record para concisão
public record SenhaRequestDTO(
        @NotNull(message = "O ID do serviço não pode ser nulo")
        Long servicoId,
        @NotNull(message = "A prioridade não pode ser nula")
        @Min(value = 1, message = "A prioridade deve ser no mínimo 0")
        Integer prioridade
) {

}