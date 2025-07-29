package com.GerenciadorDeFila.gerenciador_filas_online.controller;

import com.GerenciadorDeFila.gerenciador_filas_online.dto.FilaControllerDTO;
import com.GerenciadorDeFila.gerenciador_filas_online.services.FilaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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




}