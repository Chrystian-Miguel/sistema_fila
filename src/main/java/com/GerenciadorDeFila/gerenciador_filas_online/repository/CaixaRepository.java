package com.GerenciadorDeFila.gerenciador_filas_online.repository;
import com.GerenciadorDeFila.gerenciador_filas_online.model.Caixa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaixaRepository extends JpaRepository<Caixa, Long> {
}
