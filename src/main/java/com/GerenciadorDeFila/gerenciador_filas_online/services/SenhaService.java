package com.GerenciadorDeFila.gerenciador_filas_online.services;

import com.GerenciadorDeFila.gerenciador_filas_online.infra.exceptions.RecursoNaoEncontradoException;
import com.GerenciadorDeFila.gerenciador_filas_online.infra.exceptions.ValidacaoNegocioException;
import com.GerenciadorDeFila.gerenciador_filas_online.model.*;
import com.GerenciadorDeFila.gerenciador_filas_online.repository.PrioridadeRepository;
import com.GerenciadorDeFila.gerenciador_filas_online.repository.SenhaRepository;
import com.GerenciadorDeFila.gerenciador_filas_online.repository.ServicoRepository;
import com.GerenciadorDeFila.gerenciador_filas_online.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class SenhaService {

    private final SenhaRepository senhaRepository;
    private final ServicoRepository servicoRepository;
    private final PrioridadeRepository prioridadeRepository;
    private final StatusRepository statusRepository;

    @Autowired
    public SenhaService(SenhaRepository senhaRepository, ServicoRepository servicoRepository, PrioridadeRepository prioridadeRepository, StatusRepository statusRepository) {

        this.senhaRepository = senhaRepository;
        this.servicoRepository = servicoRepository;
        this.prioridadeRepository = prioridadeRepository;
        this.statusRepository = statusRepository;
    }


    /**
     * Gera uma nova senha com base no serviço e na prioridade, validando todas as regras de negócio.
     * O formato da senha será: [PrefixoPrioridade]-[TagSetor]-[TagServico]-[Numeração]
     * Ex: P-REC-GER-0001
     *
     * @param servicoId       O ID do serviço para o qual a senha será gerada.
     * @param nivelPrioridade O nível numérico da prioridade (ex: 0 para Normal, 1 para Preferencial).
     * @return A SenhaChamada recém-criada.
     * @throws RecursoNaoEncontradoException se o serviço ou prioridade não existirem.
     * @throws ValidacaoNegocioException     se houver uma inconsistência nos dados (ex: serviço sem setor).
     */
    @Transactional
    public SenhaChamada gerarNovaSenha(Long servicoId, Integer nivelPrioridade) {
        Servico servico = servicoRepository.findById(servicoId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Serviço não encontrado com ID: " + servicoId));

        Prioridade prioridadeEntity = prioridadeRepository.findByNivel(nivelPrioridade)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Prioridade não encontrada para o nível: " + nivelPrioridade));

        Setor setor = servico.getSetor();
        if (setor == null) {
            throw new ValidacaoNegocioException("O serviço com ID " + servicoId + " não possui um setor associado.");
        }

        String tagSetor = setor.getTag();
        String tagServico = servico.getTag();

        if (tagSetor == null || tagSetor.isBlank() || tagServico == null || tagServico.isBlank()) {
            throw new ValidacaoNegocioException("As tags de setor ou serviço não estão configuradas corretamente.");
        }

        // Encontrar o próximo número sequencial
        LocalDateTime inicioDoDia = LocalDateTime.now().toLocalDate().atStartOfDay();
        int proximoNumero = senhaRepository.findMaxNumeroSequencialByServicoIdAndData(servicoId, inicioDoDia)
                .map(max -> max + 1) // Se encontrou um máximo, some 1
                .orElse(1);          // Se não, comece com 1

        // Construir a senha formatada
        String prefixoPrioridade = prioridadeEntity.getDescricao().substring(0, 1).toUpperCase();
        String prefixo = String.format("%s-%s-%s", prefixoPrioridade, tagSetor, tagServico);
        String numeroFormatado = String.format("%04d", proximoNumero);
        String senhaCompleta = String.format("%s-%s", prefixo, numeroFormatado);

        // Retona o status de aguardadndo
        Status statusAgardando = statusRepository.findByDescricao("AGUARDANDO")
                .orElseThrow(() -> new RecursoNaoEncontradoException("Status 'AGUARDANDO' não encontrado. Verifique os status cadastrados"));

        // Criar e salvar a nova SenhaChamada
        SenhaChamada novaSenha = new SenhaChamada();
        novaSenha.setSenha(senhaCompleta);
        novaSenha.setNumeroSequencial(proximoNumero);
        novaSenha.setPrioridade(prioridadeEntity);
        novaSenha.setStatus(statusAgardando);
        novaSenha.setServico(servico);
        novaSenha.setDataDeCriacao(LocalDateTime.now());

        return senhaRepository.save(novaSenha);
    }
}
