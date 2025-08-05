package com.GerenciadorDeFila.gerenciador_filas_online.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "caixa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Caixa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String descricao; // Ex: "Caixa 01 - Recepção", "Guichê Financeiro 03"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "setor_id", nullable = false)
    private Setor setor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atendente_id")
    private Atendente atendente;
}