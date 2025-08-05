package com.GerenciadorDeFila.gerenciador_filas_online.repository;

import com.GerenciadorDeFila.gerenciador_filas_online.model.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SenhaRepository extends JpaRepository<SenhaChamada, Long> {


    List<SenhaChamada> findTop4ByStatusAndDataDeConclusaoGreaterThanEqualOrderByDataDeConclusaoDesc(Status status, LocalDateTime dataInicio);

    List<SenhaChamada> findByServicoSetorOrderByDataDeCriacaoDesc(Setor setor);


    @Query("SELECT MAX(s.numeroSequencial) FROM SenhaChamada s WHERE s.servico.id = :servicoId AND s.dataDeCriacao >= :inicioDoDia")
    Optional<Integer> findMaxNumeroSequencialByServicoIdAndData(
            @Param("servicoId") Long servicoId,
            @Param("inicioDoDia") LocalDateTime inicioDoDia
    );


    @Query("SELECT s FROM SenhaChamada s JOIN s.prioridade p " +
            "WHERE s.servico = :servico " +
            "AND s.status = :status " +
            "AND p IN :prioridades " + // Filtra pela lista de prioridades
            "AND s.dataDeCriacao >= :inicioDoDia " +
            "ORDER BY p.nivel DESC, s.dataDeCriacao ASC")
        // Ordena por nivel e depois por data
    List<SenhaChamada> findNextByPriorityList(
            @Param("servico") Servico servico,
            @Param("status") Status status,
            @Param("prioridades") List<Prioridade> prioridades,
            @Param("inicioDoDia") LocalDateTime inicioDoDia,
            Pageable pageable);
}