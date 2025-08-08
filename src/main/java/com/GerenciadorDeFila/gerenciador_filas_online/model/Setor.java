package com.GerenciadorDeFila.gerenciador_filas_online.model;

import jakarta.persistence.*;
import lombok.*;


@Entity(name = "setor")
@Table(name = "setor")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Setor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, unique = true, length = 100)
    private String nome;

    @Column(name = "tag" , nullable = false, unique = true, length = 3)
    private String tag;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    // Construtor padrão (necessário para JPA)
    // serao gerados com Lombok


    // Construtor com parâmetros (opcional)
    // serao gerados com Lombok

    // --- Getters e Setters ---
    // serao gerados com Lombok


}