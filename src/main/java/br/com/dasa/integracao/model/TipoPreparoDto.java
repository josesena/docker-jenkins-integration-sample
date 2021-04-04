package br.com.dasa.integracao.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(value = Include.NON_NULL)
@Data
public class TipoPreparoDto implements Serializable {

	private static final long serialVersionUID = 5457882017906837501L;

	private Integer id;
	
	private String nome;
	
	private String codigoApi;
	
	private String tipoValor;
}
