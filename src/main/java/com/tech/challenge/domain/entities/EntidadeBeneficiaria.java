package com.tech.challenge.domain.entities;

import jakarta.persistence.*;
import lombok.Data;


@Entity
@Table(name="EntidadeBeneficiaria")
@Data
public class EntidadeBeneficiaria {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String cnpj;
    private String nome;
    private String endereco;
    private String telefone;
    private String email;
}
