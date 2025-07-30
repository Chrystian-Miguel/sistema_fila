package com.GerenciadorDeFila.gerenciador_filas_online.repository;

import com.GerenciadorDeFila.gerenciador_filas_online.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Optional<Status> findByDescricao(String descricao);
}
