package com.GerenciadorDeFila.gerenciador_filas_online.controller;

import com.GerenciadorDeFila.gerenciador_filas_online.dto.NovaSenhaRequestDTO;
import com.GerenciadorDeFila.gerenciador_filas_online.dto.NovaSenhaRespostaDTO;
import com.GerenciadorDeFila.gerenciador_filas_online.model.SenhaChamada;
import com.GerenciadorDeFila.gerenciador_filas_online.services.SenhaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/senha")
public class SenhaController {

    private final SenhaService senhaService;

    @Autowired
    public SenhaController(SenhaService senhaService) {
        this.senhaService = senhaService;
    }
    @PostMapping ("/gerar-senha")
    @Operation(summary = "Gerar uma nova senha",
            description = "Gera uma nova senha com base no serviço e na prioridade informados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Senha gerada com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Falha ao tentar gerar a senha")
            })
    public ResponseEntity<NovaSenhaRespostaDTO> gerarSenha(@Valid @RequestBody NovaSenhaRequestDTO senhaRequest, UriComponentsBuilder uriBuilder) {
        SenhaChamada novaSenha = senhaService.gerarNovaSenha(senhaRequest.servicoId(), senhaRequest.prioridade());
        NovaSenhaRespostaDTO responseDTO = new NovaSenhaRespostaDTO(novaSenha);
        URI uri = uriBuilder.path("/api/senha/{id}").buildAndExpand(novaSenha.getId()).toUri();
        return ResponseEntity.created(uri).body(responseDTO);
    }


}
