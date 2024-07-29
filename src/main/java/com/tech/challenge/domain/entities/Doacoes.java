package com.tech.challenge.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="Doacoes")
@Data
public class Doacoes {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private Integer modalidade;
	private String status;
	private Double quantidade;
	private String medida;
	private Double valorDoadoReais;
	
}
