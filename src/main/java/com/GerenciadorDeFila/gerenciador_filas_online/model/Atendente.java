package com.GerenciadorDeFila.gerenciador_filas_online.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "atendente")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Atendente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(length = 255)
    private String descricao; // Ex: "Especialista em suporte técnico", "Atendente financeiro júnior"
}