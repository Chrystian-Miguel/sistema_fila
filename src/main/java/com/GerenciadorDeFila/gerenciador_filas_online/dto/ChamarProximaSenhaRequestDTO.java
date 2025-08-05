package com.GerenciadorDeFila.gerenciador_filas_online.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record ChamarProximaSenhaRequestDTO(
        @NotNull(message = "O ID do atendente não pode ser nulo.")
        @Positive(message = "O ID do atendente deve ser um número positivo.")
        Long atendenteId,

        @NotNull(message = "O ID do caixa não pode ser nulo.")
        @Positive(message = "O ID do caixa deve ser um número positivo.")
        Long caixaId,

        @NotNull(message = "O ID do serviço não pode ser nulo.")
        @Positive(message = "O ID do serviço deve ser um número positivo.")
        Long servicoId
) {}