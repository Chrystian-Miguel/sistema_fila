package com.GerenciadorDeFila.gerenciador_filas_online.services;

import com.GerenciadorDeFila.gerenciador_filas_online.infra.exceptions.RecursoNaoEncontradoException;
import com.GerenciadorDeFila.gerenciador_filas_online.model.*;
import com.GerenciadorDeFila.gerenciador_filas_online.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntityFinderService {

    private final StatusRepository statusRepository;
    private final PrioridadeRepository prioridadeRepository;
    private final AtendenteRepository atendenteRepository;
    private final CaixaRepository caixaRepository;
    private final ServicoRepository servicoRepository;

    @Autowired
    public EntityFinderService(StatusRepository statusRepository,
                               PrioridadeRepository prioridadeRepository,
                               AtendenteRepository atendenteRepository,
                               CaixaRepository caixaRepository,
                               ServicoRepository servicoRepository) {
        this.statusRepository = statusRepository;
        this.prioridadeRepository = prioridadeRepository;
        this.atendenteRepository = atendenteRepository;
        this.caixaRepository = caixaRepository;
        this.servicoRepository = servicoRepository;
    }

    public Status findStatusByDescricao(String descricao) {
        return statusRepository.findByDescricao(descricao)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Status '" + descricao + "' não configurado no sistema."));
    }

    public Prioridade findPrioridadeByDescricao(String descricao) {
        return prioridadeRepository.findByDescricao(descricao)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Prioridade '" + descricao + "' não configurada no sistema."));
    }

    public Atendente findAtendenteById(Long id) {
        return atendenteRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Atendente com ID " + id + " não encontrado."));
    }

    public Caixa findCaixaById(Long id) {
        return caixaRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Caixa com ID " + id + " não encontrado."));
    }

    public Servico findServicoById(Long id) {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Serviço com ID " + id + " não encontrado."));
    }

    public List<Prioridade> findAllPrioridades() {
        return prioridadeRepository.findAll();
    }
}