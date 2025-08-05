package com.GerenciadorDeFila.gerenciador_filas_online.model;

import jakarta.persistence.*;
import lombok.*;


@Entity(name = "servico")
@Table(name = "servico")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    @Column(name = "tempo_medio_atendimento")
    private Integer tempoMedioAtendimento;

    @Column(name = "tag" , nullable = false, unique = true, length = 3)
    private String tag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "setor_id", nullable = false)
    private Setor setor;


    // Construtor padrão (necessário para JPA)
    // serao gerados com Lombok


    // Construtor com parâmetros (opcional)
    // serao gerados com Lombok

    // --- Getters e Setters ---
    // serao gerados com Lombok



}
