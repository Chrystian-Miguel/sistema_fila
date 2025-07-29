package com.GerenciadorDeFila.gerenciador_filas_online.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "senha_chamada")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SenhaChamada {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "senha", nullable = false, length = 50)
    private String senha;

    @Column(name = "numero_sequencial", nullable = false)
    private Integer numeroSequencial; // <-- NOVO CAMPO! Guardará o número puro add na V4


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prioridade_id", nullable = false)
    private Prioridade prioridade;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servico_id", nullable = false)
    private Servico servico;

    @Column(name = "data_de_criacao", nullable = false)
    private LocalDateTime dataDeCriacao;

    @Column(name = "data_de_processamento")
    private LocalDateTime dataDeProcessamento;

    @Column(name = "data_de_conclusao")
    private LocalDateTime dataDeConclusao;

    @Column(name = "atendidopor", length = 50)
    private String atendidopor;

}
