package com.GerenciadorDeFila.gerenciador_filas_online.infra.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando um recurso específico (como um Serviço ou Prioridade)
 * não é encontrado no banco de dados.
 * Mapeia para o status HTTP 404 Not Found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecursoNaoEncontradoException extends RuntimeException {
    public RecursoNaoEncontradoException(String message) {
        super(message);
    }
}