package br.com.dasa.integracao.model.enums;

public enum ErroEnum {

	PRICE_NOT_FOUND("erro-preco", "Valor indisponível"),
	DEADLINE_NOT_FOUND("erro-prazo", "Prazo indisponível"),
	VARIABLE_NOT_FOUND("erro-variaveis", "Variaveis indisponível"),
	BRAND_NOT_FOUND("erro-marca", "Marca indisponível"),
	INTERNAL_ERROR("erro-interno", "Desculpe, ocorreu um erro interno.");
	
	
	private String code;
	
	private String descritpion;
	
	private ErroEnum(String code, String description) {
		this.code = code;
		this.descritpion = description;
	}

	public String getCode() {
		return code;
	}

	public String getDescritpion() {
		return descritpion;
	}


}
