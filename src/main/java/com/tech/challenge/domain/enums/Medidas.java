package com.tech.challenge.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Medidas {

	KG("Kg"),
	LITRO("Litro/s"),
	SACO("Saco"),
	PACOTE("Pacote"),
	CAIXA("Caixa"),
	ENGRADADO("Engradado"),
	UNIDADE("Unidade");
	
	private String descricao;
}
