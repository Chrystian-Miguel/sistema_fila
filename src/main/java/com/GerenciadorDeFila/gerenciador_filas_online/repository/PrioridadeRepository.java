package com.GerenciadorDeFila.gerenciador_filas_online.repository;

import com.GerenciadorDeFila.gerenciador_filas_online.model.Prioridade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrioridadeRepository extends JpaRepository<Prioridade,Long> {


    Optional<Prioridade> findByNivel(Integer nivelPrioridade);
}
