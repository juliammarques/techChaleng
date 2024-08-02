package com.tech.challenge.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="ItensDisponiveisDoacoes")
@Data
public class ItensDisponiveisDoacoes {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int id_entidade;
	private String descricao;
	private Double quantidade;
	private String status;
	private String medida;
	
}
