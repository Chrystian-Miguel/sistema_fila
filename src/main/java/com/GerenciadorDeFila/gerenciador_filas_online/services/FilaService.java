package com.GerenciadorDeFila.gerenciador_filas_online.services;

import com.GerenciadorDeFila.gerenciador_filas_online.dto.FilaControllerDTO;
import com.GerenciadorDeFila.gerenciador_filas_online.infra.exceptions.RecursoNaoEncontradoException;
import com.GerenciadorDeFila.gerenciador_filas_online.model.SenhaChamada;
import com.GerenciadorDeFila.gerenciador_filas_online.model.Servico;
import com.GerenciadorDeFila.gerenciador_filas_online.model.Setor;
import com.GerenciadorDeFila.gerenciador_filas_online.model.Status;
import com.GerenciadorDeFila.gerenciador_filas_online.repository.SenhaRepository;
import com.GerenciadorDeFila.gerenciador_filas_online.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilaService {

    private final SenhaRepository senhaRepository;
    private final StatusRepository statusRepository;

    @Autowired
    public FilaService(SenhaRepository senhaRepository, StatusRepository statusRepository) {
        this.senhaRepository = senhaRepository;
        this.statusRepository = statusRepository;
    }

    //  O método retorna uma lista de DTOs com as 4 ultimas senhas atendidas no dia atual

    @Transactional(readOnly = true)
    public List<FilaControllerDTO> getUltimaSenhaAtendida() {

        Status statusConcluido = statusRepository.findByDescricao("CONCLUIDO")
                .orElseThrow(() -> new RecursoNaoEncontradoException("Status 'CONCLUIDO' não encontrado. Verifique os status cadastrados"));

        // Define o início do dia atual (hoje, à meia-noite)
        LocalDateTime inicioDoDia = LocalDate.now().atStartOfDay();

        // Usa o novo método do repositório para filtrar por status e data de conclusão a partir do início do dia
        List<SenhaChamada> senhasConcluidas = senhaRepository
                .findTop4ByStatusAndDataDeConclusaoGreaterThanEqualOrderByDataDeConclusaoDesc(statusConcluido, inicioDoDia);

        return senhasConcluidas.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Converte uma entidade SenhaChamada para um FilaControllerDTO
     */
    private FilaControllerDTO convertToDto(SenhaChamada senha) {
        String nomeServico = "Serviço não informado";
        String nomeSetor = "Setor não informado";

        if (senha.getServico() != null) {
            Servico servico = senha.getServico();
            nomeServico = servico.getDescricao();
            if (servico.getSetor() != null) {
                Setor setor = servico.getSetor();
                nomeSetor = setor.getNome();
            }
        }

        String statusString = (senha.getStatus() != null) ? senha.getStatus().getDescricao() : "STATUS_INDISPONÍVEL";
        String atendidoPor = (senha.getAtendidopor() != null) ? senha.getAtendidopor() : "N/A";

        return new FilaControllerDTO(
                senha.getSenha(),
                nomeServico,
                senha.getDataDeConclusao(),
                senha.getDataDeCriacao(),
                atendidoPor,
                nomeSetor,
                statusString
        );
    }
}
