package com.GerenciadorDeFila.gerenciador_filas_online.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.boot.jaxb.hbm.internal.RepresentationModeConverter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;
@Entity
@Table(name = "senha_chamada")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SenhaChamada extends RepresentationModel<SenhaChamada> {


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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atendente_id") // A senha agora tem um vínculo com um atendente
    private Atendente atendente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "caixa_id") // E também com um caixa
    private Caixa caixa;

}
