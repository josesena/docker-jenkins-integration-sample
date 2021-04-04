package br.com.dasa.integracao.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import br.com.dasa.integracao.model.enums.ErroEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@JsonInclude(value = Include.NON_NULL)
@Data
public class ErrorDto implements Serializable {

	private static final long serialVersionUID = 7262703016952544999L;
	
	private String code;
	
	private String message;
	
	private String exception;
	
	public ErrorDto(ErroEnum erro, String exception) {
		this.code = erro.getCode();
		this.message = erro.getDescritpion();
		this.exception = exception;
	}

}
