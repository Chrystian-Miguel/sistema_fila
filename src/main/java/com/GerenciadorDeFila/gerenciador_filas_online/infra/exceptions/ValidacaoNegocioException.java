package com.GerenciadorDeFila.gerenciador_filas_online.infra.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção para erros de validação que violam as regras de negócio.
 * Mapeia para o status HTTP 400 Bad Request.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidacaoNegocioException extends RuntimeException {
    public ValidacaoNegocioException(String message) {
        super(message);
    }
}