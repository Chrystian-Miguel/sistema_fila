package com.GerenciadorDeFila.gerenciador_filas_online.controller;

import com.GerenciadorDeFila.gerenciador_filas_online.dto.SenhaRequestDTO;
import com.GerenciadorDeFila.gerenciador_filas_online.dto.SenhaRespostaDTO;
import com.GerenciadorDeFila.gerenciador_filas_online.model.SenhaChamada;
import com.GerenciadorDeFila.gerenciador_filas_online.services.SenhaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    @PostMapping ("/gerar")
    // O método agora retorna o DTO de resposta.
    public ResponseEntity<SenhaRespostaDTO> gerarSenha(@Valid @RequestBody SenhaRequestDTO senhaRequest, UriComponentsBuilder uriBuilder) {
        SenhaChamada novaSenha = senhaService.gerarNovaSenha(senhaRequest.servicoId(), senhaRequest.prioridade());

        // Mapeia a entidade para o DTO de resposta.
        SenhaRespostaDTO responseDTO = new SenhaRespostaDTO(novaSenha);

        // Boa prática: Retorna a URL do recurso criado no cabeçalho 'Location'.
        URI uri = uriBuilder.path("/api/senha/{id}").buildAndExpand(novaSenha.getId()).toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

}
