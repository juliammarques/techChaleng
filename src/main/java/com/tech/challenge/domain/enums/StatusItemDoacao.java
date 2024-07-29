package com.tech.challenge.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusItemDoacao {

	ATIVO("Ativo"),
	INATIVO("Inativo");
	
	private String descricao;

}
