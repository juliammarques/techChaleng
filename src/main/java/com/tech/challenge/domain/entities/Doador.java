package com.tech.challenge.domain.entities;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="Doador")
@Data
public class Doador {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String cpfcnpj;
    private String nome;
    private String endereco;
    private String telefone;
    private String email;
}
