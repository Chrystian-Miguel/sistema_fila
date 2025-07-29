package com.GerenciadorDeFila.gerenciador_filas_online.infra;

import com.GerenciadorDeFila.gerenciador_filas_online.infra.exceptions.RecursoNaoEncontradoException;
import com.GerenciadorDeFila.gerenciador_filas_online.infra.exceptions.ValidacaoNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Classe centralizadora para tratamento de exceções em todos os controllers.
 * Converte exceções específicas em respostas HTTP padronizadas.
 */
@RestControllerAdvice
public class TratadorDeErros {

    private static final Logger log = LoggerFactory.getLogger(TratadorDeErros.class);

    // Injeta a propriedade 'spring.profiles.active' do application.properties
    @Value("${spring.profiles.active:prod}")
    private String activeProfile;

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroDTO> tratarErro404(RecursoNaoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroDTO("Recurso não encontrado", ex.getMessage()));
    }

    @ExceptionHandler(ValidacaoNegocioException.class)
    public ResponseEntity<ErroDTO> tratarErro400(ValidacaoNegocioException ex) {
        return ResponseEntity.badRequest().body(new ErroDTO("Regra de negócio violada", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> tratarErro500(Exception ex) {
        log.error("Erro inesperado interceptado:", ex);

        // Passo 2: Verificar se estamos em ambiente de desenvolvimento.
        if ("dev".equals(activeProfile)) {
            // Em 'dev', retorna uma resposta detalhada para facilitar a depuração.
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            String stackTrace = sw.toString();

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErroDetalhadoDTO("Erro Interno do Servidor", ex.getMessage(), ex.getClass().getName(), stackTrace));
        }

        // Em produção, retorna a mensagem genérica para não expor detalhes de implementação.
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErroDTO("Erro interno do servidor", "Ocorreu um erro inesperado. Contate o suporte."));
    }

    // DTO privado para padronizar a resposta de erro.
    private record ErroDTO(String erro, String mensagem) {}
    private record ErroDetalhadoDTO(String erro, String mensagem, String tipoExcecao, String stackTrace) {}
}