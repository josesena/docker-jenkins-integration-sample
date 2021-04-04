package br.com.dasa.integracao.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@JsonInclude(value = Include.NON_NULL)
@Data
public class PreparoDto implements Serializable {

	private static final long serialVersionUID = 3015962281514051051L;
	
	private String descricao;
	
	private String nomePerfil;
	
	private String nomeFaixaEtaria;
	
	private TipoPreparoDto tipoPreparo;
	
}
