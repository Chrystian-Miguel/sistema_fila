package com.GerenciadorDeFila.gerenciador_filas_online.repository;

import com.GerenciadorDeFila.gerenciador_filas_online.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
}
