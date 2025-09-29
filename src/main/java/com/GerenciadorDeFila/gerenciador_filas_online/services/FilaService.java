package com.GerenciadorDeFila.gerenciador_filas_online.services;

import com.GerenciadorDeFila.gerenciador_filas_online.dto.FilaControllerDTO;
import com.GerenciadorDeFila.gerenciador_filas_online.infra.exceptions.tratamendoDeErros.RecursoNaoEncontradoException;
import com.GerenciadorDeFila.gerenciador_filas_online.mapper.FilaMapper;
import com.GerenciadorDeFila.gerenciador_filas_online.model.*;
import com.GerenciadorDeFila.gerenciador_filas_online.repository.SenhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilaService {

    private final SenhaRepository senhaRepository;
    private final ChamarSenhasService entityFinderService;
    private final FilaMapper filaMapper;

    @Autowired
    public FilaService(SenhaRepository senhaRepository,
                       ChamarSenhasService entityFinderService,
                       FilaMapper filaMapper ) {
        this.senhaRepository = senhaRepository;
        this.filaMapper = filaMapper;
        this.entityFinderService = entityFinderService;
    }



    //  O metodo retorna uma lista de DTOs com as 4 ultimas senhas atendidas no dia atual

    @Transactional(readOnly = true)
    public List<FilaControllerDTO> getUltimaSenhaAtendida() {

        Status statusConcluido = entityFinderService.findStatusByDescricao("CONCLUIDO");

        LocalDateTime inicioDoDia = LocalDate.now().atStartOfDay();

        List<SenhaChamada> senhasConcluidas = senhaRepository
                .findTop4ByStatusAndDataDeConclusaoGreaterThanEqualOrderByDataDeConclusaoDesc(statusConcluido, inicioDoDia);
        return senhasConcluidas.stream()
                .map(filaMapper::toDTO)
                .collect(Collectors.toList());
    }



    // chamar a proxima senha na fila somente preferecia normal e urgente (chaixa exclusivo atendimento normal)

    @Transactional
    public FilaControllerDTO chamarProximaSenhaNormal(Long atendenteId, Long caixaId, Long servicoId) {
        Prioridade prioridadeNormal = entityFinderService.findPrioridadeByDescricao("NORMAL");
        Prioridade prioridadeUrgente = entityFinderService.findPrioridadeByDescricao("URGENTE");
        return chamarProximaSenha(atendenteId, caixaId, servicoId, List.of(prioridadeNormal, prioridadeUrgente));
    }


     // chamar a proxima senha na fila somente preferecial e urgente (chaixa exclusivo atendimento preferencial)

    @Transactional
    public FilaControllerDTO chamarProximaSenhaPrioritaria(Long atendenteId, Long caixaId, Long servicoId) {
        Prioridade prioridadePreferencial = entityFinderService.findPrioridadeByDescricao("PREFERENCIAL");
        Prioridade prioridadeUrgente = entityFinderService.findPrioridadeByDescricao("URGENTE");
        return chamarProximaSenha(atendenteId, caixaId, servicoId, List.of(prioridadePreferencial, prioridadeUrgente));
    }

    // chamar a proxima senha respeitando o nivel da prioridade (caixa misto)

    @Transactional
    public FilaControllerDTO chamarProximaSenhaDisponivel(Long atendenteId, Long caixaId, Long servicoId) {
        List<Prioridade> todasAsPrioridades = entityFinderService.findAllPrioridades();
        return chamarProximaSenha(atendenteId, caixaId, servicoId, todasAsPrioridades);
    }



    private FilaControllerDTO chamarProximaSenha(Long atendenteId, Long caixaId, Long servicoId, List<Prioridade> prioridades) {
        Status statusAguardando = entityFinderService.findStatusByDescricao("AGUARDANDO");
        Status statusEmAtendimento = entityFinderService.findStatusByDescricao("EM_ATENDIMENTO");
        Atendente atendente = entityFinderService.findAtendenteById(atendenteId);
        Caixa caixa = entityFinderService.findCaixaById(caixaId);
        Servico servico = entityFinderService.findServicoById(servicoId);
        LocalDateTime inicioDoDia = LocalDate.now().atStartOfDay();
        Pageable limit = PageRequest.of(0, 1);
        SenhaChamada senhaParaChamar = senhaRepository
                .findNextByPriorityList(servico, statusAguardando, prioridades, inicioDoDia, limit)
                .stream().findFirst()
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Nenhuma senha aplicável na fila de espera para o serviço: " + servico.getDescricao()));

        senhaParaChamar.setStatus(statusEmAtendimento);
        senhaParaChamar.setDataDeProcessamento(LocalDateTime.now());
        senhaParaChamar.setAtendente(atendente);
        senhaParaChamar.setCaixa(caixa);

        senhaRepository.save(senhaParaChamar);
        return filaMapper.toDTO(senhaParaChamar);
    }








}
