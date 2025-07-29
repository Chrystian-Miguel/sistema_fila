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

    //  O método  retorna uma lista de DTOs com as 4 ultimas senhas atendidas
    @Transactional(readOnly = true)
    public List<FilaControllerDTO> getUltimaSenhaAtendida() {

        Status statusConcluido = statusRepository.findByDescricao("CONCLUIDO")
                .orElseThrow(() -> new RecursoNaoEncontradoException("Status 'CONCLUIDO' not found. Check initial database setup."));

        // IMPROVEMENT 2: The query is now type-safe, using the Status entity.
        // This assumes the repository method signature is updated to accept a Status object.
        List<SenhaChamada> senhasConcluidas = senhaRepository.findTop4ByStatusOrderByDataDeConclusaoDesc(statusConcluido);

        // Delega a lógica de conversão para um método privado, tornando o código mais limpo.
        return senhasConcluidas.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Converte uma entidade SenhaChamada para um FilaControllerDTO de forma segura.
     *
     * @param senha A entidade a ser convertida.
     * @return O DTO preenchido.
     */
    private FilaControllerDTO convertToDto(SenhaChamada senha) {
        // FIX 1: Safely handle all potential NullPointerExceptions by checking each level of the object graph.
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

        // FIX 2: Use the safe variables to construct the DTO, preventing runtime errors.
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
