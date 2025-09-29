package com.GerenciadorDeFila.gerenciador_filas_online.infra.exceptions.tratamendoDeErros;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidacaoNegocioException extends RuntimeException {
    public ValidacaoNegocioException(String message) {
        super(message);
    }
}