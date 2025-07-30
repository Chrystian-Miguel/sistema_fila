package com.GerenciadorDeFila.gerenciador_filas_online.repository;

import com.GerenciadorDeFila.gerenciador_filas_online.model.SenhaChamada;
import com.GerenciadorDeFila.gerenciador_filas_online.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SenhaRepository extends JpaRepository<SenhaChamada, Long> {

    // Retorna as 4 ultimas sehas chamadas do dia atual

    List<SenhaChamada> findTop4ByStatusAndDataDeConclusaoGreaterThanEqualOrderByDataDeConclusaoDesc(Status status, LocalDateTime dataInicio);


    /**
     * Encontra o maior número sequencial para uma combinação específica de prefixo de senha e serviço,
     * considerando apenas as senhas criadas na data fornecida.
     * O prefixo da senha é composto por: prioridade-tagSetor-tagServico.
     * Esta consulta nativa MySQL extrai a parte numérica após o último hífen.
     */
    @Query("SELECT MAX(s.numeroSequencial) FROM SenhaChamada s WHERE s.servico.id = :servicoId AND s.dataDeCriacao >= :inicioDoDia")
    Optional<Integer> findMaxNumeroSequencialByServicoIdAndData(
            @Param("servicoId") Long servicoId,
            @Param("inicioDoDia") LocalDateTime inicioDoDia
    );


}
