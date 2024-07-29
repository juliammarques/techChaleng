package com.tech.challenge.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusDoacoes {

	SOLICITADO("Solicitado"),
	A_CAMINHO("A caminho"),
	ENTREGUE("Entregue"),
	REAGENDAR("Reagendar o dia");
	
	private String descricao;
	
}
