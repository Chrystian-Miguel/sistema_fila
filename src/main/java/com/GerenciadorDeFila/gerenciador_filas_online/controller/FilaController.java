package com.GerenciadorDeFila.gerenciador_filas_online.controller;

import com.GerenciadorDeFila.gerenciador_filas_online.dto.ChamarProximaSenhaRequestDTO;
import com.GerenciadorDeFila.gerenciador_filas_online.dto.FilaControllerDTO;
import com.GerenciadorDeFila.gerenciador_filas_online.services.FilaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fila")
public class FilaController {

    private final FilaService filaService;

    @Autowired
    public FilaController(FilaService filaService) {
        this.filaService = filaService;
    }

    @GetMapping("/ultima-4-atendida")
    public ResponseEntity<List<FilaControllerDTO>> getUltimasAtendidas() { // <-- Retorna List<UltimaSenhaDTO>
        List<FilaControllerDTO> ultimasSenhas = filaService.getUltimaSenhaAtendida();
        return ResponseEntity.ok(ultimasSenhas);
    }


    @PostMapping("/chamar-proxima")
    @Operation(summary = "Chama a próxima senha disponível para um serviço (com prioridade automática)",
            description = "Busca a senha de maior prioridade (Urgente > Preferencial > Normal) na fila para o serviço especificado, atualiza seu status para 'EM_ATENDIMENTO' e a associa ao atendente e caixa informados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Senha chamada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"), // <-- Boa prática: adicionar resposta de erro
                    @ApiResponse(responseCode = "404", description = "Nenhuma senha encontrada na fila ou recurso (atendente, caixa, etc.) não encontrado")
            })
    public ResponseEntity<FilaControllerDTO> chamarProximaSenha(

            @Parameter(description = "Objeto JSON contendo os IDs do atendente, caixa e serviço.")
            @RequestBody @Valid ChamarProximaSenhaRequestDTO requestDTO) {


        FilaControllerDTO proximaSenha = filaService.chamarProximaSenhaDisponivel(
                requestDTO.atendenteId(),
                requestDTO.caixaId(),
                requestDTO.servicoId()
        );
        return ResponseEntity.ok(proximaSenha);
    }


    @PostMapping("/chamar-proxima-normal")
    @Operation(summary = "Chama a próxima senha disponível para um serviço (com prioridade normal)",
            description = "Busca a senha de maior prioridade (Urgente > normal) na fila para o serviço especificado, atualiza seu status para 'EM_ATENDIMENTO' e a associa ao atendente e caixa informados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Senha chamada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"), // <-- Boa prática: adicionar resposta de erro
                    @ApiResponse(responseCode = "404", description = "Nenhuma senha (normal) encontrada na fila ou recurso (atendente, caixa, etc.) não encontrado")
            })
    public ResponseEntity<FilaControllerDTO> chamarProximaSenhaNormal(

            @Parameter(description = "Objeto JSON contendo os IDs do atendente, caixa e serviço.")
            @RequestBody @Valid ChamarProximaSenhaRequestDTO requestDTO) {


        FilaControllerDTO proximaSenha = filaService.chamarProximaSenhaDisponivel(
                requestDTO.atendenteId(),
                requestDTO.caixaId(),
                requestDTO.servicoId()
        );
        return ResponseEntity.ok(proximaSenha);
    }



    @PostMapping("/chamar-proxima-prioritaria")
    @Operation(summary = "Chama a próxima senha disponível para um serviço (com prioridade Prioritaria)",
            description = "Busca a senha de maior prioridade (Urgente > Prioritaria) na fila para o serviço especificado, atualiza seu status para 'EM_ATENDIMENTO' e a associa ao atendente e caixa informados.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Senha chamada com sucesso"),
                    @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"), // <-- Boa prática: adicionar resposta de erro
                    @ApiResponse(responseCode = "404", description = "Nenhuma senha (Prioritaria) encontrada na fila ou recurso (atendente, caixa, etc.) não encontrado")
            })
    public ResponseEntity<FilaControllerDTO> chamarProximaSenhaPrioritaria(

            @Parameter(description = "Objeto JSON contendo os IDs do atendente, caixa e serviço.")
            @RequestBody @Valid ChamarProximaSenhaRequestDTO requestDTO) {


        FilaControllerDTO proximaSenha = filaService.chamarProximaSenhaDisponivel(
                requestDTO.atendenteId(),
                requestDTO.caixaId(),
                requestDTO.servicoId()
        );
        return ResponseEntity.ok(proximaSenha);
    }
}



